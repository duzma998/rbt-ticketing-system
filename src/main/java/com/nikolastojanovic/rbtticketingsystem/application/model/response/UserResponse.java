package com.nikolastojanovic.rbtticketingsystem.application.model.response;

public record UserResponse(
        Long Id,
        String username,
        String email,
        String firstName,
        String lastName
) {}
