package com.project.ProgettoSad.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ExceptionGameNotFound extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ExceptionGameNotFound(String message) {
		super(message);
	}
	
	public ExceptionGameNotFound(String message, Throwable throwable) {
		super(message,throwable);
	}
}
