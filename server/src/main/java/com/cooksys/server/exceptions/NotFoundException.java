package com.cooksys.server.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotFoundException extends RuntimeException {
	/**
	   * 
	   */
	private static final long serialVersionUID = 1507282063883666846L;
	private String message;
}
