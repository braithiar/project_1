package com.revature.dao;

import com.revature.models.ExpenseType;
import com.revature.models.Reimbursement;
import com.revature.models.Status;
import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Queue;

public interface ReimbursementDAO extends
  JpaRepository<Reimbursement, Integer> {
  List<Reimbursement> findByUser(User user);
  List<Reimbursement> findByUserAndStatus(User user, Status status);
  List<Reimbursement> findByUserAndType(User user, ExpenseType type);
  List<Reimbursement> findByStatus(Status status);
  List<Reimbursement> findByType(ExpenseType type);
}
