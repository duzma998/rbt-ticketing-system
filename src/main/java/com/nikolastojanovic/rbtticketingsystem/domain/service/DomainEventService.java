package com.nikolastojanovic.rbtticketingsystem.domain.service;

import com.nikolastojanovic.rbtticketingsystem.domain.exception.Error;
import com.nikolastojanovic.rbtticketingsystem.domain.exception.TicketingException;
import com.nikolastojanovic.rbtticketingsystem.domain.in.EventService;
import com.nikolastojanovic.rbtticketingsystem.domain.in.TicketService;
import com.nikolastojanovic.rbtticketingsystem.domain.in.UserService;
import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.Page;
import com.nikolastojanovic.rbtticketingsystem.domain.model.common.PageResult;
import com.nikolastojanovic.rbtticketingsystem.domain.model.request.EventRequest;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.EventRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Objects;

@AllArgsConstructor
@Service
public class DomainEventService implements EventService {

    private final EventRepository eventRepository;
    private final UserService userService;
    private final TicketService ticketService;

    @Override
    public PageResult<Event> getEvents(@NonNull Page page) {
        return eventRepository.getEvents(page);
    }

    @Override
    public Event getEvent(@NonNull Long id) {
        return eventRepository.getEvent(id);
    }

    @Override
    public Event createEvent(@NonNull EventRequest request) {
        var creator = userService.getUserByUsername(request.username());

        if(request.eventDate().isBefore(ZonedDateTime.now())) {
            throw new TicketingException(Error.BAD_REQUEST, "Event cannot be created in the past.");
        }

        var event = buildEventFromRequest(request)
                .createdBy(request.username())
                .creatorId(creator.Id())
                .build();

        var savedEvent = eventRepository.saveEvent(event);
        ticketService.initializeTicketsForEvent(savedEvent);
        return savedEvent;
    }

    @Override
    public Event updateEvent(@NonNull Long eventId, @NonNull EventRequest request) {
        var existingEvent = eventRepository.getEvent(eventId);

        var updatedEvent = Event.builder()
                .name(getValueOrDefault(request.name(), existingEvent.name()))
                .description(getValueOrDefault(request.description(), existingEvent.description()))
                .eventType(getValueOrDefault(request.eventType(), existingEvent.eventType()))
                .venueName(getValueOrDefault(request.venueName(), existingEvent.venueName()))
                .venueAddress(getValueOrDefault(request.venueAddress(), existingEvent.venueAddress()))
                .eventDate(getValueOrDefault(request.eventDate(), existingEvent.eventDate()))
                .totalTickets(getValueOrDefault(request.totalTickets(), existingEvent.totalTickets()))
                .maxTicketsPerPurchase(getValueOrDefault(request.maxTicketsPerPurchase(), existingEvent.maxTicketsPerPurchase()))
                .ticketPrice(getValueOrDefault(request.ticketPrice(), existingEvent.ticketPrice()))
                .build();

        return eventRepository.updateEvent(updatedEvent);
    }

    @Override
    public void deleteEvent(@NonNull Long eventId) {
        eventRepository.deleteEvent(eventId);
    }

    private Event.EventBuilder buildEventFromRequest(EventRequest request) {
        return Event.builder()
                .name(request.name())
                .description(request.description())
                .eventType(request.eventType())
                .venueName(request.venueName())
                .venueAddress(request.venueAddress())
                .eventDate(request.eventDate())
                .totalTickets(request.totalTickets())
                .availableTickets(request.availableTickets())
                .maxTicketsPerPurchase(request.maxTicketsPerPurchase())
                .ticketPrice(request.ticketPrice());
    }

    private <T> T getValueOrDefault(T newValue, T defaultValue) {
        return Objects.requireNonNullElse(newValue, defaultValue);
    }
}
