package com.nikolastojanovic.rbtticketingsystem.domain.exception;

import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final Error error;

    public CustomException(Error error, Object... params) {
        super(error.getLabel() + "; " + Arrays.stream(params).map(String::valueOf)
                .collect(Collectors.joining("; ")));
        this.error = error;
    }
}
