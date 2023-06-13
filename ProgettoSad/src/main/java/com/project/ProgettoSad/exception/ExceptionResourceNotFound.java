package com.project.ProgettoSad.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ExceptionResourceNotFound extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ExceptionResourceNotFound(String message) {
		super(message);
	}
	
	public ExceptionResourceNotFound(String message, Throwable throwable) {
		super(message,throwable);
	}
}
