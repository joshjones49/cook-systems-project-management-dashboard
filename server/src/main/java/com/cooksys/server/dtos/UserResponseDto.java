package com.cooksys.server.dtos;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponseDto {

	private Long id;
	private UserProfileDto profile;
	private String username;
	private boolean active;
	private boolean admin;
	private String status;
	private List<CompanyResponseDto> companies;
	private List<TeamResponseDto> teams;
}
