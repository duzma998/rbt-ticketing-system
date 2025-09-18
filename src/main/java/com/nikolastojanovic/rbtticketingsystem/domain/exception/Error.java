package com.nikolastojanovic.rbtticketingsystem.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Error {
    NOT_FOUND(404, "Not Found"),
    VALIDATION_ERROR(400, "Validation failed"),
    FORBIDDEN(403, "Forbidden");
    private final Integer status;
    private final String label;
}
