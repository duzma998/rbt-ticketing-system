package com.nikolastojanovic.rbtticketingsystem.application.mapper;

import com.nikolastojanovic.rbtticketingsystem.application.model.request.ApplicationLoginRequest;
import com.nikolastojanovic.rbtticketingsystem.application.model.request.ApplicationSignUpRequest;
import com.nikolastojanovic.rbtticketingsystem.application.model.response.UserResponse;
import com.nikolastojanovic.rbtticketingsystem.domain.model.User;
import com.nikolastojanovic.rbtticketingsystem.domain.model.request.LoginRequest;
import com.nikolastojanovic.rbtticketingsystem.domain.model.request.SignUpRequest;
import org.springframework.stereotype.Component;

@Component
public class ApplicationUserMapper {

    public LoginRequest toLoginRequest(ApplicationLoginRequest loginRequest) {
        return new LoginRequest(loginRequest.username(), loginRequest.password());
    }

    public SignUpRequest toSignUpRequest(ApplicationSignUpRequest signUpRequest) {
        return new SignUpRequest(signUpRequest.username(), signUpRequest.email(),signUpRequest.firstName(),signUpRequest.lastName(), signUpRequest.password());
    }

    public UserResponse toUserResponse(User user) {
        return new UserResponse(user.Id(), user.username(), user.email(), user.firstName(), user.lastName());
    }
}
