package com.nikolastojanovic.rbtticketingsystem.domain.exception;

import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class TicketingException extends RuntimeException {

    private final Error error;

    public TicketingException(Error error, Object... params) {
        super(Arrays.stream(params).map(String::valueOf)
                .collect(Collectors.joining("; ")));
        this.error = error;
    }
}
