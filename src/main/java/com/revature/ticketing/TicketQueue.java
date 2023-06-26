package com.revature.ticketing;

import com.revature.models.Reimbursement;

import java.util.ArrayDeque;
import java.util.Queue;

public class TicketQueue {
  private static Queue<Reimbursement> pendingTickets = new ArrayDeque<>();

  public static boolean addTicket(Reimbursement r) {
    return pendingTickets.offer(r);
  }

  public static Reimbursement getTicket() {
    return pendingTickets.peek();
  }

  public static Reimbursement removeTicket() {
    return pendingTickets.poll();
  }
}
