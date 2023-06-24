package com.revature.services;

import com.revature.dao.ReimbursementDAO;
import com.revature.dao.StatusDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Reimbursement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReimbursementService {
  private final UserDAO userDAO;
  private final ReimbursementDAO reimbDAO;
  private final StatusDAO statusDAO;

  @Autowired
  public ReimbursementService(UserDAO userDAO, ReimbursementDAO reimbDAO,
                              StatusDAO statusDAO) {
    this.userDAO = userDAO;
    this.reimbDAO = reimbDAO;
    this.statusDAO = statusDAO;
  }

  public Reimbursement submitReimbursement(Reimbursement r) {
    int pendingId = statusDAO.getStatusIdByName("Pending");
    Reimbursement newReimbursement = null;

    if (r.getStatusID() != pendingId) {
      r.setStatusID(pendingId);
    }

    if (r.getAmount() > 0 && !r.getDescription().isEmpty() &&
        r.getUserID() > 0) {
      newReimbursement = reimbDAO.save(r);
    }

    if (newReimbursement.getId() > 0) {
      // TODO: log success
      return newReimbursement;
    }
    // TODO: log failure
    return null;
  }

  public List<Reimbursement> getUserPendingReimbursements(int uid) {
    int sid = statusDAO.getStatusIdByName("Pending");

    return reimbDAO.getAllReimbursementsByUserIdAndStatusId(uid, sid);
  }

  public List<Reimbursement> getUserApprovedReimbursements(int uid) {
    int sid = statusDAO.getStatusIdByName("Approved");

    return reimbDAO.getAllReimbursementsByUserIdAndStatusId(uid, sid);
  }

  public List<Reimbursement> getAllPendingReimbursements() {
    int sid = statusDAO.getStatusIdByName("Pending");

    return reimbDAO.getAllReimbursementsByStatusId(sid);
  }

  public List<Reimbursement> getAllApprovedReimbursements() {
    int sid = statusDAO.getStatusIdByName("Approved");

    return reimbDAO.getAllReimbursementsByStatusId(sid);
  }

  public Reimbursement updateReimbursement(Reimbursement r) {
    return reimbDAO.save(r);
  }
}
