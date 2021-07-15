package com.br.recycle.api.exception;

public class MethodNotAllowedException  extends RuntimeException {
	private static final long serialVersionUID = 1L;

    public MethodNotAllowedException(String message) {
        super(message);
    }
}
