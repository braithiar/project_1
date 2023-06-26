package com.revature.exceptions;

public class FailedStatusUpdateException extends RuntimeException {
  public FailedStatusUpdateException() {
    super("Could not update reimbursement status");
  }
}
