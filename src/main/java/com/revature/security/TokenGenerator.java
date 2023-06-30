package com.revature.security;

import com.revature.dao.UserDAO;
import com.revature.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class TokenGenerator {
  private final int expiration;
  private final SecretKey secretKey;
  private final UserDAO userDAO;

  @Autowired
  public TokenGenerator(@Value("${jwt.secret}") String secret,
                        @Value("${jwt.expirationDateInMs}") int expiration,
                        UserDAO userDAO) {
    this.expiration = expiration;
    secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA512");
    this.userDAO = userDAO;
  }

  public String generateToken(Authentication auth) {
    String username = auth.getName();
    Date current = new Date();
    Date expire = new Date(current.getTime() + expiration);
    User user = userDAO.findByUsername(username);


    return Jwts.builder()
               .setSubject(username)
               .claim("Id", user.getId())
               .claim("Role", user.getRole().getTitle())
               .setIssuedAt(current)
               .setExpiration(expire)
               .signWith(secretKey, SignatureAlgorithm.HS512)
               .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(token);

      return true;
    } catch (Exception e) {
      throw new AuthenticationCredentialsNotFoundException(
        "JWT token is expired or invalid");
    }
  }

  public String getUsernameFromToken(String token) {
    if (token != null && token.startsWith("Bearer")) {
      token = token.substring(7);
    }

    Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

    return claims.getSubject();
  }
}
