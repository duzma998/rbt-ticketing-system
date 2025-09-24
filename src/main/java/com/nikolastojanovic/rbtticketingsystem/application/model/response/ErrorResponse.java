package com.nikolastojanovic.rbtticketingsystem.application.model.response;

import lombok.Builder;

@Builder
public record ErrorResponse(
    String error,
    String message
) {}
