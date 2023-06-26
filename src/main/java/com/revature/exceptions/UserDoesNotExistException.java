package com.revature.exceptions;

public class UserDoesNotExistException extends RuntimeException {
  public UserDoesNotExistException(int uid) {
    super("No user exists with id: " + uid);
  }

  public UserDoesNotExistException(String username) {
    super("No user exists with username: " + username);
  }

  public UserDoesNotExistException() {
    super("Could not find user");
  }
}
