package com.revature.controllers;

import com.revature.dao.RoleDAO;
import com.revature.dao.UserDAO;
import com.revature.dto.AuthDTO;
import com.revature.dto.LoginDTO;
import com.revature.dto.RegisterDTO;
import com.revature.exceptions.UsernameAlreadyInUseException;
import com.revature.models.User;
import com.revature.security.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class AuthController {
  private final AuthenticationManager authManager;
  private final UserDAO userDAO;
  private final RoleDAO roleDAO;
  private final PasswordEncoder passEncoder;
  private final TokenGenerator tokenGenerator;

  @Autowired
  public AuthController(AuthenticationManager authManager, UserDAO userDAO,
                        RoleDAO roleDAO, PasswordEncoder passEncoder,
                        TokenGenerator tokenGenerator) {
    this.authManager = authManager;
    this.userDAO = userDAO;
    this.roleDAO = roleDAO;
    this.passEncoder = passEncoder;
    this.tokenGenerator = tokenGenerator;
  }

  @PostMapping("register")
  public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
    if (userDAO.existsByUsername(registerDTO.getUsername())) {
      throw new UsernameAlreadyInUseException();
    }

    User user = new User(
      registerDTO.getFirstName(),
      registerDTO.getLastName(),
      registerDTO.getUsername(),
      passEncoder.encode(registerDTO.getPassword())
    );

    user.setRole(roleDAO.findRoleByTitle("Employee"));

    return new ResponseEntity<>(userDAO.save(user), HttpStatus.CREATED);
  }

  @PostMapping("login")
  public ResponseEntity<AuthDTO> login(@RequestBody LoginDTO loginDTO) {
    Authentication authentication = authManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginDTO.getUsername(),
        loginDTO.getPassword()
      )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return new ResponseEntity<>(
      new AuthDTO(tokenGenerator.generateToken(authentication)),
      HttpStatus.OK
    );
  }
}
