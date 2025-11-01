package com.cooksys.server.dtos;

import com.cooksys.server.entities.Company;
import com.cooksys.server.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AnnouncementRequestDto {

	private String title;
	private String message;
	private CompanyRequestDto company;
	private UserRequestDto author;
}
