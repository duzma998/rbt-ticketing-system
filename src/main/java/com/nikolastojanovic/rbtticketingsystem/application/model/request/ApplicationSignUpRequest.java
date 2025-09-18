package com.nikolastojanovic.rbtticketingsystem.application.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ApplicationSignUpRequest(
    @NotEmpty
    String username,
    @NotEmpty
    String email,
    @NotEmpty
    String firstname,
    @NotEmpty
    String lastname,
    @NotEmpty
    @Size(min = 8, max = 20)
    String password
) {

}
