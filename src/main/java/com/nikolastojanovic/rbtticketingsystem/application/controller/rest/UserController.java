package com.nikolastojanovic.rbtticketingsystem.application.controller.rest;

import com.nikolastojanovic.rbtticketingsystem.application.model.request.ApplicationLoginRequest;
import com.nikolastojanovic.rbtticketingsystem.application.model.request.ApplicationSignUpRequest;
import com.nikolastojanovic.rbtticketingsystem.application.model.response.UserResponse;
import com.nikolastojanovic.rbtticketingsystem.application.service.ApplicationUserService;
import com.nikolastojanovic.rbtticketingsystem.domain.model.response.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final ApplicationUserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid ApplicationLoginRequest loginRequest) {
        var token = userService.login(loginRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@RequestBody @Valid ApplicationSignUpRequest signUpRequest) {
        return ResponseEntity.ok(userService.signUp(signUpRequest));
    }
}
