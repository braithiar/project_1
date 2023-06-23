package com.revature.dto;

public class AuthDTO {
  private String token;
  private final String tokenType;

  public AuthDTO(String token) {
    this.token = token;
    tokenType = "Bearer";
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getTokenType() {
    return tokenType;
  }
}
