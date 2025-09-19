package com.nikolastojanovic.rbtticketingsystem.domain.service;

import com.nikolastojanovic.rbtticketingsystem.domain.exception.CustomException;
import com.nikolastojanovic.rbtticketingsystem.domain.exception.Error;
import com.nikolastojanovic.rbtticketingsystem.domain.in.UserService;
import com.nikolastojanovic.rbtticketingsystem.domain.model.User;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.UserRole;
import com.nikolastojanovic.rbtticketingsystem.domain.model.request.LoginRequest;
import com.nikolastojanovic.rbtticketingsystem.domain.model.request.SignUpRequest;
import com.nikolastojanovic.rbtticketingsystem.domain.model.response.LoginResponse;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.UserRepository;
import com.nikolastojanovic.rbtticketingsystem.domain.out.service.AuthenticationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class DomainUserService implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Override
    public LoginResponse login(@NonNull LoginRequest request) {
        var token = authenticationService.login(request);
        return new LoginResponse(request.username(), token);
    }

    @Override
    public User signUp(@NonNull SignUpRequest request) {
        if (userRepository.getByUsername(request.username()).isPresent()) {
            throw new CustomException(Error.VALIDATION_ERROR, "Username already exists");
        }
        var user = User.builder().username(request.username())
                .firstName(request.firstname())
                .email(request.email())
                .lastName(request.lastname())
                .role(UserRole.USER)
                .password(authenticationService.encodePassword(request.password()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return userRepository.save(user);
    }

    @Override
    public User getUserByUsername(@NonNull String username) {
        return userRepository.getByUsername(username)
                .orElseThrow(() -> new CustomException(Error.NOT_FOUND, "User not found"));
    }
}
