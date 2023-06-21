package com.revature.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final CustomUDService customUDService;

  @Autowired
  public SecurityConfig(CustomUDService customUDService) {
    this.customUDService = customUDService;
  }
}
