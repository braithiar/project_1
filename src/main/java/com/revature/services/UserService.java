package com.revature.services;

import com.revature.dao.UserDAO;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  private final UserDAO userDAO;

  @Autowired
  public UserService(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  public User addUser(User user) {
    User returned = userDAO.save(user);

    if (returned.getId() > 0) {
      // TODO: log success
      return returned;
    }

    // TODO: log failure
    return null;
  }

  public User updateUser(User user) {
    User returned = userDAO.save(user);

    if (returned != null) {
      // TODO: log success
      return returned;
    }

    // TODO: log failure
    return null;
  }

  public List<User> getAllUsers() {
    return userDAO.findAll();
  }

  public User getUserById(int uid) {
    Optional<User> returned = userDAO.findById(uid);

    if (returned.isPresent()) {
      // TODO: log success
      return returned.get();
    }

    // TODO: log failure
    return null;
  }

  public boolean deleteUser(int uid) {
    userDAO.deleteById(uid);

    return !userDAO.existsById(uid);
  }
}
