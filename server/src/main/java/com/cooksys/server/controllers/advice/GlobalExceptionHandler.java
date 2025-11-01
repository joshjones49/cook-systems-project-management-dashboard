package com.cooksys.server.controllers.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cooksys.server.dtos.ErrorDto;
import com.cooksys.server.exceptions.BadRequestException;
import com.cooksys.server.exceptions.NotFoundException;
import com.cooksys.server.exceptions.NotAuthorizedException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackages = {"com.cooksys.server.controllers"})
@ResponseBody
public class GlobalExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorDto handleNotFoundException(HttpServletRequest request, NotFoundException notFoundException) {
		return new ErrorDto(notFoundException.getMessage());
	}
	
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDto handleBadRequestException(HttpServletRequest request, BadRequestException badRequestException) {
		return new ErrorDto(badRequestException.getMessage());
	}
	
	@ExceptionHandler(NotAuthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorDto handleNotAuthorizedException(HttpServletRequest request, NotAuthorizedException notAuthorizedException) {
		return new ErrorDto(notAuthorizedException.getMessage());
	}
	
}
