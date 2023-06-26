package com.revature.exceptions;

public class FailedReimbursementException extends RuntimeException {
  public FailedReimbursementException() {
    super("Reimbursement could not be created");
  }
}
