package com.revature.exceptions;

public class UsernameAlreadyInUseException extends RuntimeException {
  public UsernameAlreadyInUseException() {
    super("Username already exists!");
  }
}
