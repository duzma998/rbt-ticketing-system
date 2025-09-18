package com.nikolastojanovic.rbtticketingsystem.application.mapper;

import com.nikolastojanovic.rbtticketingsystem.application.model.response.EventResponse;
import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventMapper {

    public EventResponse toResponse(Event event) {
        return new EventResponse(
                event.id(),
                event.name(),
                event.description(),
                event.eventType(),
                event.venueName(),
                event.venueAddress(),
                event.eventDate(),
                event.totalTickets(),
                event.availableTickets(),
                event.maxTicketsPerPurchase(),
                event.ticketPrice(),
                event.status(),
                event.createdBy(),
                event.createdAt(),
                event.updatedAt(),
                null
        );
    }
}
