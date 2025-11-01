package com.cooksys.server.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.cooksys.server.dtos.UserCredentialsDto;
import com.cooksys.server.dtos.UserRequestDto;
import com.cooksys.server.dtos.UserResponseDto;
import com.cooksys.server.entities.Company;
import com.cooksys.server.entities.User;
import com.cooksys.server.exceptions.BadRequestException;
import com.cooksys.server.exceptions.InvalidPasswordException;
import com.cooksys.server.exceptions.NotFoundException;
import com.cooksys.server.mappers.UserMapper;
import com.cooksys.server.repositories.CompanyRepository;
import com.cooksys.server.repositories.UserRepository;
import com.cooksys.server.services.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final CompanyRepository companyRepository;

	/**
	 * Method to login a user
	 * 
	 * @param userCredentialsDto the provided username and password credentials
	 */
	@Override
	public UserResponseDto login(UserCredentialsDto userCredentialsDto) {

		// Ensure credentials exist in request body
		if (userCredentialsDto == null) {
			throw new BadRequestException("UserRequestDto cannot be null");
		}

		// Validate credentials fields with helper method
		validateField(userCredentialsDto.getUsername(), "Username is required");
		validateField(userCredentialsDto.getPassword(), "Password is required");

		// Provided credentials
		String providedUsername = userCredentialsDto.getUsername();
		String providedPassword = userCredentialsDto.getPassword();

		// Check for existing user by provided username
		Optional<User> userToFind = userRepository.findByCredentialsUsername(providedUsername);
		if (userToFind.isEmpty()) {
			throw new NotFoundException("No user found with username " + providedUsername);
		}

		User existingUser = userToFind.get();

		// Check provided password against password in database
		if (!BCrypt.checkpw(providedPassword, existingUser.getCredentials().getPassword())) {
			throw new InvalidPasswordException("Incorrect password provided");
		}

		// Set active and status fields on first log in
		if (!existingUser.isActive()) {
			existingUser.setActive(true);
		}
		if (existingUser.getStatus().equalsIgnoreCase("PENDING")) {
			existingUser.setStatus("ACTIVE");
		}

		return userMapper.entityToDto(userRepository.saveAndFlush(existingUser));
	}

	/**
	 * Creates a new user in the database and returns that user if the dto passed in
	 * is valid
	 * 
	 * @param userRequestDto
	 */
	@Override
	public UserResponseDto createUser(Long companyId, UserRequestDto userRequestDto) {
		// Validate using helper method
		validateUserRequestDto(userRequestDto);

		// Map the new user
		User newUser = userMapper.requestDtoToEntity(userRequestDto);

		// Encrypt password and save new user
		String hashedPassword = BCrypt.hashpw(newUser.getCredentials().getPassword(), BCrypt.gensalt(12));
		newUser.getCredentials().setPassword(hashedPassword);
		userRepository.saveAndFlush(newUser);

		// Initialize the user's status as "PENDING"
		newUser.setStatus("PENDING");

		// Associate user with current company
		Optional<Company> currentCompany = companyRepository.findById(companyId);
		if (currentCompany.isPresent()) {
			Company company = currentCompany.get();

			if (newUser.getCompanies() == null) {
				newUser.setCompanies(new ArrayList<>());
			}
			if (company.getUsers() == null) {
				company.setUsers(new ArrayList<>());
			}
			
			newUser.getCompanies().add(company);
			company.getUsers().add(newUser);
			companyRepository.saveAndFlush(company);
		} else if (currentCompany.isEmpty()) {
			throw new NotFoundException("No company found with id " + companyId);
		}

		// Return new user
		return userMapper.entityToDto(userRepository.saveAndFlush(newUser));
	}

	/**
	 * Method to manually update a users active, status, or admin fields if needed.
	 * 
	 * @param userRequestDto the fields to update
	 */
	@Override
	public UserResponseDto updateUser(String username, UserRequestDto userRequestDto) {
		// Ensure request isn't empty
		if (userRequestDto == null) {
			throw new BadRequestException("UserRequestDto cannot be null");
		}

		// Find existing user
		Optional<User> existingUser = userRepository.findByCredentialsUsername(username);
		if (existingUser.isEmpty()) {
			throw new NotFoundException("No user found with username " + username);
		}

		User userToUpdate = existingUser.get();

		// Update admin, status, and/or active if provided. Ignore other fields.
		if (userRequestDto.getStatus() != null) {
			userToUpdate.setStatus(userRequestDto.getStatus());
		}
		if (userRequestDto.getActive() != null) {
			userToUpdate.setActive(userRequestDto.getActive());
		}
		if (userRequestDto.getAdmin() != null) {
			userToUpdate.setAdmin(userRequestDto.getAdmin());
		}

		return userMapper.entityToDto(userRepository.saveAndFlush(userToUpdate));
	}

	@Override
	public List<UserResponseDto> getUsers(Long companyId) {
		List<User> usersForCompany = userRepository.findAllByCompaniesId(companyId);

		return userMapper.entitiesToDtos(usersForCompany);
	}

	/**
	 * Helper method to validate the fields on a UserRequestDto for user
	 * creation/registration
	 * 
	 * @param userRequestDto
	 */
	private void validateUserRequestDto(UserRequestDto userRequestDto) {
		// Check if DTO is null
		if (userRequestDto == null) {
			throw new BadRequestException("UserRequestDto cannot be null");
		}

		// Check profile and credentials
		if (userRequestDto.getProfile() == null) {
			throw new BadRequestException("Profile is required");
		}
		if (userRequestDto.getCredentials() == null) {
			throw new BadRequestException("Credentials are required");
		}

		// Validate profile fields
		validateField(userRequestDto.getProfile().getFirstName(), "First name is required");
		validateField(userRequestDto.getProfile().getLastName(), "Last name is required");
		validateField(userRequestDto.getProfile().getEmail(), "Email is required");

		// Validate email format
		String email = userRequestDto.getProfile().getEmail();
		if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			throw new BadRequestException("Invalid email format");
		}
		// Validate credentials fields
		validateField(userRequestDto.getCredentials().getUsername(), "Username is required");
		validateField(userRequestDto.getCredentials().getPassword(), "Password is required");

		// Check username uniqueness
		if (userRepository.findByCredentialsUsername(userRequestDto.getCredentials().getUsername()).isPresent()) {
			throw new BadRequestException("Username is already taken");
		}

	}

	/**
	 * Helper method to validate single dto field
	 * 
	 * @param field        the field you want to validate
	 * @param errorMessage the error message you want in the exception if validation
	 *                     fails
	 */
	private void validateField(String field, String errorMessage) {
		if (field == null || field.isEmpty()) {
			throw new BadRequestException(errorMessage);
		}
	}

}
