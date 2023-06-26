package com.revature.exceptions;

public class EmptyDescriptionException extends RuntimeException {
  public EmptyDescriptionException() {
    super("A description was not provided for the reimbursement");
  }
}
