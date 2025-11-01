package com.cooksys.server.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CompanyRequestDto {

	private String name;
	private String description;
}
