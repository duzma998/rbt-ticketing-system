package com.nikolastojanovic.rbtticketingsystem.domain.model;

import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.EventStatus;
import lombok.Builder;
import lombok.With;


import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
@With
public record Event(
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
        BigDecimal ticketPrice,
        EventStatus status,
        String createdBy,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt,
        Long creatorId
) {
}
