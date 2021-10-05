package com.example.onlinephoneshop.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		// creating exception response structure
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		// returning exception structure and specific status
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	// override method of ResponseEntityExceptionHandler class
	public final ResponseEntity<Object> handleResourceNotFoundExceptions(ResourceNotFoundException ex, WebRequest request) {
		// creating exception response structure
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		// returning exception structure and Not Found status
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResourceAlreadyExistedException.class)
	// override method of ResponseEntityExceptionHandler class
	public final ResponseEntity<Object> handleResourceAlreadyExistedException(ResourceAlreadyExistedException ex, WebRequest request) {
		// creating exception response structure
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		// returning exception structure and Not Found status
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(CustomException.class)
	// override method of ResponseEntityExceptionHandler class
	public final ResponseEntity<Object> handleCustomException(CustomException ex, WebRequest request) {
		// creating exception response structure
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		// returning exception structure and Not Found status
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}