package com.nikolastojanovic.rbtticketingsystem.domain.model.request;

import java.time.ZonedDateTime;

public record EventRequest(
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
        String username
) {}
