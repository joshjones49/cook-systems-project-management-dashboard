package com.cooksys.server.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequestDto {

	private Long id;
	private UserProfileDto profile;
	private UserCredentialsDto credentials;
	private Boolean admin;
	private Boolean active;
	private String status;
}
