package com.cooksys.server.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserCredentialsDto {
	  private String username;
	  private String password;
}