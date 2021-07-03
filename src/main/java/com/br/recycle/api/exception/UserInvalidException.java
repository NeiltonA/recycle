package com.br.recycle.api.exception;

public class UserInvalidException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserInvalidException(String message) {
	  super(message);
	}
}
