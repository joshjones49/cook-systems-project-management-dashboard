package com.cooksys.server.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotAuthorizedException extends RuntimeException {
	/**
	   * 
	   */
	private static final long serialVersionUID = 1798787656846L;
	private String message;

}
