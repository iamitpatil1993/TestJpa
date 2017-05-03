package com.example.ejb.jpa.exceptions;

import javax.ejb.ApplicationException;

//mark this as application exception and set rollback true so that, container will rollback transaction
@ApplicationException(rollback = true)
public class InsufficientDataException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public InsufficientDataException() {
		//Nothing to do here
	}
	
	public InsufficientDataException(String message) {
		
		this.message = message;
	}

	@Override
	public String toString() {
		return "InsufficientDataException [message=" + message + "]";
	}
	
}
