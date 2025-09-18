package com.nikolastojanovic.rbtticketingsystem.infrastructure.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nikolastojanovic.rbtticketingsystem.domain.model.request.LoginRequest;
import com.nikolastojanovic.rbtticketingsystem.domain.out.service.AuthenticationService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final JwtServiceImpl jwtService;

  @Override
  public String login(@NonNull LoginRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.username(), request.password()));
    return jwtService.generateToken(request.username());
  }

  @Override
  public String encodePassword(@NonNull String password) {
    return passwordEncoder.encode(password);
  }

  @Override
  public boolean passwordEquals(@NonNull String password, @NonNull String encodedPassword) {
    return passwordEncoder.matches(password, encodedPassword);
  }
}
