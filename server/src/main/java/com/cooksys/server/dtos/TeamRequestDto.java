package com.cooksys.server.dtos;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TeamRequestDto {

	private String name;
	private String description;
	private List<UserRequestDto> users;

}
