package com.revature.exceptions;

public class TicketAddException extends RuntimeException {
  public TicketAddException() {
    super("Could not add ticket to queue");
  }
}
