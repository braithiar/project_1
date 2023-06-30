package com.revature.services;

import com.revature.dao.StatusDAO;
import com.revature.models.Reimbursement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

  public static Reimbursement getTicket(int id) {
    if (!pending.isEmpty()) {
      for (Reimbursement r : pending) {
        if (id == r.getId()) {
          return r;
        }
      }
    }

    return null;
  }

  public static Reimbursement removeTicket(int id) {
    Reimbursement delete = null;

    for (Reimbursement r : pending) {
      if (id == r.getId()) {
        delete = r;
        break;
      }
    }

    if (delete.getStatus().getName() != "Pending") {
      pending.remove(delete);
    }

    return null;
  }
}
