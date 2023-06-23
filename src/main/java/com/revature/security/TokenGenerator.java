package com.revature.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
public class TokenGenerator {
  private final int expiration;
  private final SecretKey secretKey;

  @Autowired
  public TokenGenerator(@Value("${jwt.secret}") String secret,
                        @Value("${jwt.expirationDateInMs}") int expiration) {
    this.expiration = expiration;
    secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA512");
  }

  public String generateToke(Authentication auth) {
    String username = auth.getName();
    Date current = new Date();
    Date expire = new Date(current.getTime() + expiration);

    System.out.println(secretKey);

    return Jwts.builder()
               .setSubject(username)
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
    Claims claims = Jwts.parserBuilder()
      .setSigningKey(secretKey)
      .build()
      .parseClaimsJws(token)
      .getBody();

    return claims.getSubject();
  }
}
