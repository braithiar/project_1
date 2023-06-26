package com.revature.dao;

import com.revature.models.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseTypeDAO extends JpaRepository<ExpenseType, Integer> {
  ExpenseType findByType(String type);
  boolean existsByType(String type);
}
