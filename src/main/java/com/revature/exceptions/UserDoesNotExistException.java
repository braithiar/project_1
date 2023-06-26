package com.revature.exceptions;

public class UserDoesNotExistException extends RuntimeException {
  public UserDoesNotExistException(int uid) {
    super("No user exists with id: " + uid);
  }
}
