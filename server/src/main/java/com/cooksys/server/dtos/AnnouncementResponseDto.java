package com.cooksys.server.dtos;

import java.sql.Timestamp;

import com.cooksys.server.entities.Company;
import com.cooksys.server.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AnnouncementResponseDto {

	private String title;
	private String message;
	private CompanyResponseDto company;
	private UserResponseDto author;
	private Timestamp date;
}
