package com.revature.services;

import com.revature.dao.RoleDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Role;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  private final UserDAO userDAO;
  private final RoleDAO roleDAO;

  @Autowired
  public UserService(UserDAO userDAO, RoleDAO roleDAO) {
    this.userDAO = userDAO;
    this.roleDAO = roleDAO;
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

  public Role getUserRoleById(int uid) {
    Optional<User> returnedUser = userDAO.findById(uid);

    if (returnedUser.isPresent()) {
      Optional<Role> returnedRole =
        roleDAO.findById(returnedUser.get().getRole().getId());

      if (returnedRole.isPresent()) {
        return returnedRole.get();
      }
    }

    return null;
  }

  public boolean deleteUser(int uid) {
    userDAO.deleteById(uid);

    return !userDAO.existsById(uid);
  }
}
