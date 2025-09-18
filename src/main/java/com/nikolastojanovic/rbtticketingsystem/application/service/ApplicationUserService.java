package com.nikolastojanovic.rbtticketingsystem.application.service;

import com.nikolastojanovic.rbtticketingsystem.application.mapper.ApplicationUserMapper;
import com.nikolastojanovic.rbtticketingsystem.application.model.request.ApplicationLoginRequest;
import com.nikolastojanovic.rbtticketingsystem.application.model.request.ApplicationSignUpRequest;
import com.nikolastojanovic.rbtticketingsystem.application.model.response.UserResponse;
import com.nikolastojanovic.rbtticketingsystem.domain.in.UserService;
import com.nikolastojanovic.rbtticketingsystem.domain.model.response.LoginResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationUserService {

    private final UserService userService;
    private final ApplicationUserMapper userMapper;

    @Transactional
    public LoginResponse login(@NonNull ApplicationLoginRequest loginRequest) {
        var request = userMapper.toLoginRequest(loginRequest);
        return userService.login(request);
    }

    @Transactional
    public UserResponse signUp(@NonNull ApplicationSignUpRequest signUpRequest) {
        var request = userMapper.toSignUpRequest(signUpRequest);
        return userMapper.toUserResponse(userService.signUp(request));
    }
}
