package com.nikolastojanovic.rbtticketingsystem.domain.in;

import com.nikolastojanovic.rbtticketingsystem.domain.model.User;
import com.nikolastojanovic.rbtticketingsystem.domain.model.request.LoginRequest;
import com.nikolastojanovic.rbtticketingsystem.domain.model.request.SignUpRequest;
import com.nikolastojanovic.rbtticketingsystem.domain.model.response.LoginResponse;
import lombok.NonNull;

public interface UserService {

    LoginResponse login(@NonNull LoginRequest request);

    User signUp(@NonNull SignUpRequest request);

    User getUserByUsername(@NonNull String username);
}
