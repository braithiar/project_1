package com.revature.exceptions;

public class ExpenseTypeDoesNotExistException extends RuntimeException {
  public ExpenseTypeDoesNotExistException() {
    super("Type does not exist or was left blank");
  }
}
