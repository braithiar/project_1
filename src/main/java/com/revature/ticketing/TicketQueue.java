package com.revature.ticketing;

import com.revature.models.Reimbursement;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Queue;

@Component
public class TicketQueue {
  private static Queue<Reimbursement> pendingTickets = new ArrayDeque<>();


}
