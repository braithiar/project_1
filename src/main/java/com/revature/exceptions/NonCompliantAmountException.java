package com.revature.exceptions;

public class NonCompliantAmountException extends RuntimeException {
  public NonCompliantAmountException(int amount) {
    super("The amount (" + amount +
          ") provided for this reimbursement did not comply with reality");
  }
}
