package com.nikolastojanovic.rbtticketingsystem.application.mapper;

import com.nikolastojanovic.rbtticketingsystem.application.model.request.ApplicationEventRequest;
import com.nikolastojanovic.rbtticketingsystem.application.model.response.EventResponse;
import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import com.nikolastojanovic.rbtticketingsystem.domain.model.request.EventRequest;
import org.springframework.stereotype.Component;

import java.security.Principal;

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


    public EventRequest toDomain(ApplicationEventRequest applicationEventRequest, String principal) {
        return new EventRequest(
                applicationEventRequest.name(),
                applicationEventRequest.description(),
                applicationEventRequest.eventType(),
                applicationEventRequest.venueName(),
                applicationEventRequest.venueAddress(),
                applicationEventRequest.eventDate(),
                applicationEventRequest.totalTickets(),
                applicationEventRequest.totalTickets(), //  availableTickets = totalTickets
                applicationEventRequest.maxTicketsPerPurchase(),
                applicationEventRequest.ticketPrice(),
                applicationEventRequest.status(),
                principal,
                applicationEventRequest.createdAt(),
                applicationEventRequest.updatedAt()
        );
    }
}
