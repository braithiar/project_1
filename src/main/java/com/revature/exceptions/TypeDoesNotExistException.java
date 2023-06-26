package com.revature.exceptions;

public class TypeDoesNotExistException extends RuntimeException {
  public TypeDoesNotExistException() {
    super("Type does not exist or was left blank");
  }
}
