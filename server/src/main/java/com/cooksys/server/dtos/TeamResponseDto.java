package com.cooksys.server.dtos;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TeamResponseDto {
	private Long id;

	private String name;
	private String description;
	private List<ProjectResponseDto> projects;
	private List<UserResponseDtoForTeamPage> users;

}
