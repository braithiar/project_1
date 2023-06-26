package com.revature.security;

import com.revature.dao.RoleDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Role;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUDService implements UserDetailsService {
  private final UserDAO userDAO;
  private final RoleDAO roleDAO;

  @Autowired
  public CustomUDService(UserDAO userDAO, RoleDAO roleDAO) {
    this.userDAO = userDAO;
    this.roleDAO = roleDAO;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws
    UsernameNotFoundException {
    User u = userDAO.findByUsername(username);

    if (u == null) {
      new UsernameNotFoundException("Could not find username: " + username);
    }

    return new org.springframework.security.core.userdetails.User(
      u.getUsername(),
      u.getPassword(),
      mapRoleToAuthority(roleDAO.findById(u.getRole().getId()).get())
    );
  }

  private Collection<GrantedAuthority> mapRoleToAuthority(Role role) {
    List<GrantedAuthority> authorities = new ArrayList<>();

    authorities.add(new SimpleGrantedAuthority(role.getTitle()));

    return authorities;
  }
}
