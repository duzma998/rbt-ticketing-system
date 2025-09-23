package com.nikolastojanovic.rbtticketingsystem.application.model.response;

public record ErrorResponse(
    String error,
    String message
) {}
