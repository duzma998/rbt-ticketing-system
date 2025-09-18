package com.nikolastojanovic.rbtticketingsystem.application.model.response;

import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.UserRole;

import java.time.LocalDateTime;

public record UserResponse(
        Long Id,
        String username,
        String email,
        String firstName,
        String lastName
) {
}
