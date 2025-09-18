package com.nikolastojanovic.rbtticketingsystem.domain.out.service;

import lombok.NonNull;
import com.nikolastojanovic.rbtticketingsystem.domain.model.request.LoginRequest;

public interface AuthenticationService {

    String login(@NonNull LoginRequest loginRequest);

    String encodePassword(@NonNull String password);

    boolean passwordEquals(@NonNull String password, @NonNull String encodedPassword);
}
