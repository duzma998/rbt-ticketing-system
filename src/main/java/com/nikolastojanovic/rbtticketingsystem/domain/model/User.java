package com.nikolastojanovic.rbtticketingsystem.domain.model;

import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.UserRole;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record User(
        Long Id,
        String username,
        String password,
        String email,
        String firstName,
        String lastName,
        UserRole role,
        LocalDateTime updatedAt,
        LocalDateTime createdAt
) {}
