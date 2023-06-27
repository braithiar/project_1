package com.revature.services;

import com.revature.dao.StatusDAO;
import com.revature.models.Reimbursement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {
  private final ReimbursementService reimbService;
  private final StatusDAO statusDAO;
  private static List<Reimbursement> pending = new ArrayList<>();

  @Autowired
  public TicketService(ReimbursementService reimbService, StatusDAO statusDAO) {
    this.reimbService = reimbService;
    this.statusDAO = statusDAO;
  }

  @PostConstruct
  private void loadTickets() {
    pending = reimbService.getAllReimbursementsByStatus(
      statusDAO.findByName("Pending")
    );
  }

  public static boolean addTicket(Reimbursement r) {
    return pending.add(r);
  }

  public static Reimbursement getTicket() {
    return pending.get(0);
  }

  public static Reimbursement removeTicket() {
    Reimbursement statusCheck = pending.get(0);

    if (statusCheck.getStatus().getName().equals("Approved") ||
        statusCheck.getStatus().getName().equals("Denied")) {
      pending.remove(0);
    }

    return null;
  }
}
