package com.teammacc.catalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = -7744773039903308466L;

	public ResourceNotFoundException(String exception) {
		super(exception);
	}
}
