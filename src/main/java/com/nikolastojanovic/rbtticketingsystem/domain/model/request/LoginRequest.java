package com.nikolastojanovic.rbtticketingsystem.domain.model.request;

public record LoginRequest(
        String username,
        String password
) {}
