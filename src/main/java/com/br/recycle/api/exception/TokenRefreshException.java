package com.br.recycle.api.exception;

public class TokenRefreshException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public TokenRefreshException(String message) {
    super(message);
  }
  
  public TokenRefreshException(String message, Throwable cause) {
      super(message, cause);
  }
}