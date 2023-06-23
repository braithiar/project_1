package com.revature.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
  private final TokenGenerator tokenGenerator;
  private final CustomUDService customUDService;

  public JwtFilter(TokenGenerator tokenGenerator,
                   CustomUDService customUDService) {
    this.tokenGenerator = tokenGenerator;
    this.customUDService = customUDService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws
    ServletException, IOException {
    String token = getJwtFromRequest(request);

    // Token validation
    if (token != null && tokenGenerator.validateToken(token)) {
      UserDetails ud = customUDService.loadUserByUsername(
        tokenGenerator.getUsernameFromToken(token)
      );

      UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(
          ud, null, ud.getAuthorities()
        );

      authToken.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(request));

      SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    filterChain.doFilter(request, response);
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String bearer = request.getHeader("Authorization");

    if (bearer != null && bearer.startsWith("Bearer")) {
      return bearer.substring(7, bearer.length());
    }

    return null;
  }
}
