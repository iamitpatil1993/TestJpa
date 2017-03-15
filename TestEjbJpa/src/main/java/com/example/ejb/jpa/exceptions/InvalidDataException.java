package com.example.ejb.jpa.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class InvalidDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public InvalidDataException() {
		super();
		//Nothing to do here
	}

	public InvalidDataException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String toString() {
		return "InvalidDataException [message=" + message + "]";
	}
}
