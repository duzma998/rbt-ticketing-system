package com.nikolastojanovic.rbtticketingsystem.application.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ApplicationLoginRequest(
    @NotEmpty
    String username,
    @NotEmpty
    @Size(min = 8, max = 20)
    String password
) {}
