package com.andrelagacione.garagemcarroapi.services.exceptions;

public class DataInegrityException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DataInegrityException(String msg) {
		super(msg);
	}
	
	public DataInegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
