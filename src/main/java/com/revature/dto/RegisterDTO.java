package com.revature.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterDTO {
  private String firstName;
  private String lastName;
  private String username;
  private String password;
}
