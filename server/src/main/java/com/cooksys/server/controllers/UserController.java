package com.cooksys.server.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.server.dtos.UserCredentialsDto;
import com.cooksys.server.dtos.UserRequestDto;
import com.cooksys.server.dtos.UserResponseDto;
import com.cooksys.server.services.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

	private final UserService userService;

	@PostMapping("/{companyId}")
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponseDto createUser(@PathVariable Long companyId, @RequestBody UserRequestDto userRequestDto) {
		return userService.createUser(companyId, userRequestDto);
	}

	@PatchMapping("/{username}")
	public UserResponseDto updateUser(@PathVariable String username, @RequestBody UserRequestDto userRequestDto) {
		return userService.updateUser(username, userRequestDto);
	}

	@PostMapping("/login")
	public UserResponseDto login(@RequestBody UserCredentialsDto userCredentialsDto) {
		return userService.login(userCredentialsDto);
	}

	@GetMapping("/{companyId}")
	public List<UserResponseDto> getUsers(@PathVariable Long companyId) {
		return userService.getUsers(companyId);
	}

}
