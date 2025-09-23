package com.nikolastojanovic.rbtticketingsystem.application.model.response;

import java.util.Map;

public record ValidationErrorResponse(
        String error,
        String message,
        Map<String, String> fieldErrors
){}