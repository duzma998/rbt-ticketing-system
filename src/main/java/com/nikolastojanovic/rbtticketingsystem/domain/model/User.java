package com.nikolastojanovic.rbtticketingsystem.domain.model;

import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.UserRole;

import java.time.LocalDateTime;

public record User(
        Long Id,
        String username,
        String email,
        String firstName,
        String lastName,
        UserRole role,
        LocalDateTime updatedAt,
        LocalDateTime createdAt
) {
}
