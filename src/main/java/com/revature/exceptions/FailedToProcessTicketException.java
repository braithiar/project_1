package com.revature.exceptions;

public class FailedToProcessTicketException extends RuntimeException {
  public FailedToProcessTicketException() {
    super(
      "This reimbursement ticket has already been processed and can no longer be changed");
  }

  public FailedToProcessTicketException(String message) {
    super(message);
  }
}
