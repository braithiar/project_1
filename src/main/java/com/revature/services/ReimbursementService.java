package com.revature.services;

import com.revature.dao.ExpenseTypeDAO;
import com.revature.dao.ReimbursementDAO;
import com.revature.dao.StatusDAO;
import com.revature.dao.UserDAO;
import com.revature.exceptions.*;
import com.revature.models.ExpenseType;
import com.revature.models.Reimbursement;
import com.revature.models.Status;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReimbursementService {
  private final UserDAO userDAO;
  private final ReimbursementDAO reimbDAO;
  private final StatusDAO statusDAO;
  private final ExpenseTypeDAO expenseTypeDAO;

  @Autowired
  public ReimbursementService(UserDAO userDAO, ReimbursementDAO reimbDAO,
                              StatusDAO statusDAO,
                              ExpenseTypeDAO expenseTypeDAO) {
    this.userDAO = userDAO;
    this.reimbDAO = reimbDAO;
    this.statusDAO = statusDAO;
    this.expenseTypeDAO = expenseTypeDAO;
  }

  public Reimbursement createReimbursement(int uid, Reimbursement r) {
    Status pending = statusDAO.findByName("Pending");
    Reimbursement newReimbursement = null;

    if (pending.getId() <= 0) {
      throw new StatusDoesNotExistException(pending.getId());
    }

    r.setStatus(pending);

    if (userDAO.existsById(uid)) {
      r.setUser(userDAO.getReferenceById(uid));
    } else {
      throw new UserDoesNotExistException(uid);
    }

    if (r.getAmount() <= 0) {
      throw new NonCompliantAmountException(r.getAmount());
    }

    if (r.getDescription().isEmpty()) {
      throw new EmptyDescriptionException();
    }

    if (r.getType() == null ||
        !expenseTypeDAO.existsById(r.getType().getId()) ||
        expenseTypeDAO.findByType(r.getType().getType()).getId() !=
        r.getType().getId()) {
      throw new TypeDoesNotExistException();
    }


    newReimbursement = reimbDAO.save(r);

    if (newReimbursement.getId() > 0) {
      return newReimbursement;
    }

    throw new FailedReimbursementException();
  }

  public List<Reimbursement> getUserReimbursementsByStatus(User user,
                                                           Status status) {
    if (!userDAO.existsById(user.getId())) {
      throw new UserDoesNotExistException(user.getId());
    }

    if (!statusDAO.existsById(status.getId())) {
      throw new StatusDoesNotExistException(status.getId());
    }

    return reimbDAO.findByUserAndStatus(
      user,
      status
    );
  }

  public List<Reimbursement> getUserReimbursementsByType(User user,
                                                         ExpenseType type) {
    if (!userDAO.existsById(user.getId())) {
      throw new UserDoesNotExistException(user.getId());
    }

    if (!expenseTypeDAO.existsById(type.getId())) {
      throw new TypeDoesNotExistException();
    }

    return reimbDAO.findByUserAndType(
      user,
      type
    );
  }

  public List<Reimbursement> getAllUserReimbursements(User user) {
    if (!userDAO.existsById(user.getId())) {
      throw new UserDoesNotExistException(user.getId());
    }

    return reimbDAO.findByUser(user);
  }

  public List<Reimbursement> getAllReimbursementsByStatus(Status status) {
    if (!statusDAO.existsById(status.getId())) {
      throw new StatusDoesNotExistException(status.getId());
    }

    return reimbDAO.findByStatus(status);
  }

  public List<Reimbursement> getAllReimbursementsByType(ExpenseType type) {
    if (!expenseTypeDAO.existsById(type.getId())) {
      throw new TypeDoesNotExistException();
    }

    return reimbDAO.findByType(type);
  }

  public List<Reimbursement> getAllReimbursements() {
    return reimbDAO.findAll();
  }

  public Reimbursement updateReimbursementStatus(Reimbursement r) {
    Status origStatus = reimbDAO.getReferenceById(r.getId()).getStatus();

    // Reimbursements may not be edited after being processed
    if (r != null && origStatus.getName() != "Approved" ||
        origStatus.getName() != "Denied") {
      return reimbDAO.save(r);
    }

    throw new FailedStatusUpdateException();
  }
}
