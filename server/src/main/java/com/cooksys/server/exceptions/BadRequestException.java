package com.cooksys.server.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BadRequestException extends RuntimeException {
	/**
	   * 
	   */
	private static final long serialVersionUID = 1698777788846L;
	private String message;

}
