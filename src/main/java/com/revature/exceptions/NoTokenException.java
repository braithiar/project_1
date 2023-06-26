package com.revature.exceptions;

public class NoTokenException extends RuntimeException {
  public NoTokenException() {
    super("Could not verify credentials");
  }
}
