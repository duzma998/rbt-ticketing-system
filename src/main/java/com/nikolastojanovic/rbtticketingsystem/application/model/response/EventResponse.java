package com.nikolastojanovic.rbtticketingsystem.application.model.response;

import com.nikolastojanovic.rbtticketingsystem.domain.model.User;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.EventStatus;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record EventResponse(
        Long id,
        String name,
        String description,
        String eventType,
        String venueName,
        String venueAddress,
        ZonedDateTime eventDate,
        Long totalTickets,
        Long availableTickets,
        Long maxTicketsPerPurchase,
        BigDecimal ticketPrice,
        EventStatus status,
        Long createdBy,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt,
        User creator) {
}
