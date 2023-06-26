package com.revature.exceptions;

public class StatusDoesNotExistException extends RuntimeException {
  public StatusDoesNotExistException(int sid) {
    super("Could not find a status with id: " + sid);
  }
}
