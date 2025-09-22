package com.nikolastojanovic.rbtticketingsystem.application.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record ApplicationOrderRequest(
        @NotNull(message = "Event ID is required")
        Long eventId,

        List<String> seats,

        @Positive(message = "Ticket count must be positive")
        Integer ticketCount
) {
}
