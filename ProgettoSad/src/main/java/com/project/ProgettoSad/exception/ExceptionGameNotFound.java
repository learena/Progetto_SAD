package com.project.ProgettoSad.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

//permette di indicare un messaggio nella risposta http a seguito del verificarsi di un'eccezione
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
