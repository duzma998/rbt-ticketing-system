package com.nikolastojanovic.rbtticketingsystem.application.model.request;

import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.EventStatus;

import java.time.ZonedDateTime;

public record ApplicationEventRequest(
        String name,
        String description,
        String eventType,
        String venueName,
        String venueAddress,
        ZonedDateTime eventDate,
        Integer totalTickets,
        Integer availableTickets,
        Integer maxTicketsPerPurchase,
        Double ticketPrice,
        EventStatus status,
        String createdBy,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt,
        String User
) {
}
