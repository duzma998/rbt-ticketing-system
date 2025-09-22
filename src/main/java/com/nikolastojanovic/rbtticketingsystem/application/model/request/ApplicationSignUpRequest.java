package com.nikolastojanovic.rbtticketingsystem.application.model.request;

import jakarta.validation.constraints.*;

public record ApplicationSignUpRequest(
        @NotBlank(message = "Username is required")
        @Size(max = 50, message = "Username must not exceed 50 characters")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "Email format is invalid")
        @Size(max = 100, message = "Email must not exceed 100 characters")
        String email,

        @NotBlank(message = "First name is required")
        @Size(max = 100, message = "First name must not exceed 100 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 100, message = "Last name must not exceed 100 characters")
        String lastName,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
        String password
) {
}
