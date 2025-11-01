package com.cooksys.server.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InvalidPasswordException extends RuntimeException {
	private static final long serialVersionUID = 2220480963522733281L;
	private String message;
}
