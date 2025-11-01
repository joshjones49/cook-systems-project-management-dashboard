package com.cooksys.server.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserProfileDto {
	  private String firstName;
	  private String lastName;
	  private String email;
	  private String phone;
}
