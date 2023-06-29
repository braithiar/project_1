package com.revature.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final CustomUDService customUDService;
  private final JwtAuthEntry jwtAuthEntry;
  private final JwtFilter jwtFilter;

  @Autowired
  public SecurityConfig(CustomUDService customUDService, JwtAuthEntry jwtAuthEntry,
                        JwtFilter jwtFilter) {
    this.customUDService = customUDService;
    this.jwtAuthEntry = jwtAuthEntry;
    this.jwtFilter = jwtFilter;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .cors()
      .and()
      .csrf().disable()
      .exceptionHandling()
      .authenticationEntryPoint(jwtAuthEntry)
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authorizeRequests()
      .antMatchers("/auth/**").permitAll()
      .antMatchers("/users/**").hasAnyAuthority("Finance Manager", "Employee")
      .antMatchers(HttpMethod.GET, "/reimbursements/**").hasAuthority("Finance Manager")
      .antMatchers(HttpMethod.PUT, "/reimbursements/**").hasAuthority("Finance Manager")
      .antMatchers(HttpMethod.POST, "/reimbursements/**").hasAuthority("Finance Manager")
      .antMatchers(HttpMethod.DELETE, "/reimbursements/**").hasAuthority("Finance Manager")
      .antMatchers(HttpMethod.POST, "/employee/reimbursements/**").hasAnyAuthority("Finance Manager", "Employee")
      .antMatchers(HttpMethod.GET, "/employee/reimbursements/**").hasAnyAuthority("Finance Manager", "Employee")
      .antMatchers(HttpMethod.DELETE, "/employee/reimbursements/**").hasAnyAuthority("Finance Manager", "Employee")
      .antMatchers("**").denyAll()
      .and()
      .httpBasic();

    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager getAuthenticationManager(
    AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
