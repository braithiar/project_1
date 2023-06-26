package com.revature.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthDTO {
  private String token;
  private final String tokenType = "Bearer";
}
