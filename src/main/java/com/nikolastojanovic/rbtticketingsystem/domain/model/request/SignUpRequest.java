package com.nikolastojanovic.rbtticketingsystem.domain.model.request;

public record SignUpRequest(
    String username,
    String email,
    String firstname,
    String lastname,
    String password
) {

}
