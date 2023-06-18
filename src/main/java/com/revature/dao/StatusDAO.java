package com.revature.dao;

import com.revature.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusDAO extends JpaRepository<Status, Integer> {
}
