package com.cooksys.server.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.server.dtos.TeamRequestDto;
import com.cooksys.server.dtos.TeamResponseDto;
import com.cooksys.server.entities.Company;
import com.cooksys.server.entities.Project;
import com.cooksys.server.entities.Team;
import com.cooksys.server.entities.User;
import com.cooksys.server.exceptions.BadRequestException;
import com.cooksys.server.exceptions.NotFoundException;
import com.cooksys.server.mappers.TeamMapper;
import com.cooksys.server.repositories.CompanyRepository;
import com.cooksys.server.repositories.ProjectRepository;
import com.cooksys.server.repositories.TeamRepository;
import com.cooksys.server.repositories.UserRepository;
import com.cooksys.server.services.TeamService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

	private final TeamRepository teamRepository;
	private final TeamMapper teamMapper;
	private final CompanyRepository companyRepository;
	private final UserRepository userRepository;

	@Override
	public List<TeamResponseDto> getCompanyTeams(long companyId) {
		return teamMapper.entitiesToDtos(teamRepository.findAllByCompanyId(companyId));
	}

	@Override
	public List<TeamResponseDto> getUserTeams(long userId) {
		return teamMapper.entitiesToDtos(teamRepository.findAllByUsersId(userId));
	}

	@Override
	public TeamResponseDto createTeam(long companyId, TeamRequestDto teamRequestDto) {
		Team newTeam = teamMapper.dtoToEntity(teamRequestDto);

		if (newTeam.getName() == null || newTeam.getName().isEmpty() || newTeam.getDescription() == null
				|| newTeam.getDescription().isEmpty()) {
			throw new BadRequestException("Error: Must include team name and description");
		}

		// Fetch the company team will be associated with
		Optional<Company> company = companyRepository.findById(companyId);
		if (company.isEmpty()) {
			throw new NotFoundException("No company found with id " + companyId);
		}

		newTeam.setCompany(company.get());

		// Save the team first to ensure it has an ID
		newTeam = teamRepository.saveAndFlush(newTeam);

		// Associate users passed in with the new team
		if (newTeam.getUsers() != null && !newTeam.getUsers().isEmpty()) {

			List<User> usersToUpdate = newTeam.getUsers().stream()
            .map(user -> userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + user.getId())))
            .toList();

        for (User user : usersToUpdate) {
            user.getTeams().add(newTeam);
            userRepository.save(user);
        }}
		return teamMapper.entityToDto(newTeam);
	}



}
