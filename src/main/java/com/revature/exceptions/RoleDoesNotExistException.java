package com.revature.exceptions;

public class RoleDoesNotExistException extends RuntimeException {
  public RoleDoesNotExistException() {
    super("Could not find user role");
  }
}
