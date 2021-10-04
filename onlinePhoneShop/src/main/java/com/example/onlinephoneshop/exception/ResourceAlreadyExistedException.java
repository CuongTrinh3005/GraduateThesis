package com.example.onlinephoneshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistedException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ResourceAlreadyExistedException(String message) {
		super(message);
	}
}