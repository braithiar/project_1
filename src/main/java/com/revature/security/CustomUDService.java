package com.revature.security;

import com.revature.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUDService implements UserDetailsService {
  private final UserDAO userDAO;

  @Autowired
  public CustomUDService(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws
    UsernameNotFoundException {
    return null;
  }
}