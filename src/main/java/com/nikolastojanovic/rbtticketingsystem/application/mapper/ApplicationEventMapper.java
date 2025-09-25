package com.nikolastojanovic.rbtticketingsystem.application.mapper;

import com.nikolastojanovic.rbtticketingsystem.application.model.request.ApplicationEventRequest;
import com.nikolastojanovic.rbtticketingsystem.application.model.response.EventResponse;
import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import com.nikolastojanovic.rbtticketingsystem.domain.model.request.EventRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventMapper {

    public EventResponse toResponse(@NotNull Event event) {
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
                event.updatedAt()
        );
    }

    public EventRequest toRequest(@NotNull ApplicationEventRequest request, @NotNull String principal) {
        return new EventRequest(
                request.name(),
                request.description(),
                request.eventType(),
                request.venueName(),
                request.venueAddress(),
                request.eventDate(),
                request.totalTickets(),
                request.totalTickets(), // availableTickets initially equals totalTickets
                request.maxTicketsPerPurchase(),
                request.ticketPrice(),
                principal
        );
    }
}