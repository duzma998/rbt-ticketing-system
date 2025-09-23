package com.nikolastojanovic.rbtticketingsystem.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.nikolastojanovic.rbtticketingsystem.domain.exception.TicketingException;
import com.nikolastojanovic.rbtticketingsystem.domain.exception.Error;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.properties.JwtProperties;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl {

  private final JwtProperties jwtProperties;

  public String generateToken(String username) {
    var now = Instant.now();
    return Jwts.builder()
        .subject(username)
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plus(jwtProperties.getExpiration(), ChronoUnit.MINUTES)))
        .signWith(getSignKey())
        .compact();
  }

  public String extractUsername(String token) {
    return getTokenBody(token).getSubject();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final var username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  private Claims getTokenBody(String token) {
    try {
      return Jwts
          .parser()
          .setSigningKey(getSignKey())
          .build()
          .parseSignedClaims(token)
          .getPayload();
    } catch (SignatureException | ExpiredJwtException e) { // Invalid signature or expired token
      throw new TicketingException(Error.FORBIDDEN, "Access denied: Invalid token");
    }
  }

  private boolean isTokenExpired(String token) {
    var claims = getTokenBody(token);
    return claims.getExpiration().before(new Date());
  }

  private Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
