package com.nikolastojanovic.rbtticketingsystem.application.model.request;

import lombok.Builder;
import jakarta.validation.constraints.*;
import java.time.ZonedDateTime;

@Builder
public record ApplicationEventRequest(
        @NotBlank(message = "Event name is required")
        @Size(max = 255, message = "Event name must not exceed 255 characters")
        String name,

        String description,

        @NotBlank(message = "Event type is required")
        @Size(max = 100, message = "Event type must not exceed 100 characters")
        String eventType,

        @Size(max = 255, message = "Venue name must not exceed 255 characters")
        String venueName,

        String venueAddress,

        @NotNull(message = "Event date is required")
        ZonedDateTime eventDate,

        @NotNull(message = "Total tickets is required")
        @Positive(message = "Total tickets must be positive")
        Integer totalTickets,

        @NotNull(message = "Max tickets per purchase is required")
        @Positive(message = "Max tickets per purchase must be positive")
        Integer maxTicketsPerPurchase,

        @NotNull(message = "Ticket price is required")
        @DecimalMin(value = "0.0", message = "Ticket price must be zero or positive")
        Double ticketPrice
) {}