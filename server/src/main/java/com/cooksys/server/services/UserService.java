package com.cooksys.server.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.server.dtos.UserCredentialsDto;
import com.cooksys.server.dtos.UserRequestDto;
import com.cooksys.server.dtos.UserResponseDto;

@Service
public interface UserService {

	UserResponseDto createUser(Long companyId, UserRequestDto userRequestDto);

	UserResponseDto updateUser(String username, UserRequestDto userRequestDto);

	UserResponseDto login(UserCredentialsDto userCredentialsDto);

	List<UserResponseDto> getUsers(Long companyId);
}
