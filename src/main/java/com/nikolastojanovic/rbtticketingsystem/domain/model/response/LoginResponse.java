package com.nikolastojanovic.rbtticketingsystem.domain.model.response;

public record LoginResponse(
        String username,
        String token
) {}
