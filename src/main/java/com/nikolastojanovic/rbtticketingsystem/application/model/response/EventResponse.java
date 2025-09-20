package com.nikolastojanovic.rbtticketingsystem.application.model.response;

import com.nikolastojanovic.rbtticketingsystem.domain.model.User;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.EventStatus;

import java.time.ZonedDateTime;

public record EventResponse(
        Long id,
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
        User creator
) {
}
