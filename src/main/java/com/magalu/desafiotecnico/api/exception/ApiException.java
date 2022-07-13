package com.magalu.desafiotecnico.api.exception;

public class ApiException extends RuntimeException {

  public ApiException(String message) {

    super(message);
  }

  public ApiException(String message, Exception ex) {

    super(message, ex);
  }

}
