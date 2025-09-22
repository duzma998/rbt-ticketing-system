package com.nikolastojanovic.rbtticketingsystem.domain.service;

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


@AllArgsConstructor
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
    public Event createEvent(@NonNull EventRequest event) {

        var creator = userService.getUserByUsername(event.username());
        var saveEvent = Event.builder()
                .name(event.name())
                .description(event.description())
                .eventType(event.eventType())
                .venueName(event.venueName())
                .venueAddress(event.venueAddress())
                .eventDate(event.eventDate())
                .totalTickets(event.totalTickets())
                .availableTickets(event.availableTickets())
                .maxTicketsPerPurchase(event.maxTicketsPerPurchase())
                .ticketPrice(event.ticketPrice())
                .status(event.status())
                .createdBy(event.username())
                .createdAt(event.createdAt())
                .updatedAt(event.updatedAt())
                .creatorId(creator.Id())
                .build();

        var savedEvent =  eventRepository.saveEvent(saveEvent);
        ticketService.initializeTicketsForEvent(savedEvent);
        return savedEvent;
    }

    @Override
    public Event updateEvent(@NonNull Long eventId, @NonNull EventRequest event) {

        var eventEntity = eventRepository.getEvent(eventId);

        var patchedEvent = Event.builder().id(eventId)
                .name(event.name() == null ? eventEntity.name() : event.name())
                .description(event.description() == null ? eventEntity.description() : event.description())
                .eventType(event.eventType() == null ? eventEntity.eventType() : event.eventType())
                .venueName(event.venueName() == null ? eventEntity.venueName() : event.venueName())
                .venueAddress(event.venueAddress() == null ? eventEntity.venueAddress() : event.venueAddress())
                .eventDate(event.eventDate() == null ? eventEntity.eventDate() : event.eventDate())
                .totalTickets(event.totalTickets() == null ? eventEntity.totalTickets() : event.totalTickets())
                .availableTickets(eventEntity.availableTickets())
                .maxTicketsPerPurchase(event.maxTicketsPerPurchase() == null ? eventEntity.maxTicketsPerPurchase() : event.maxTicketsPerPurchase())
                .ticketPrice(event.ticketPrice() == null ? eventEntity.ticketPrice() : event.ticketPrice())
                .status(event.status() == null ? eventEntity.status() : event.status())
                .createdBy(eventEntity.createdBy())
                .createdAt(eventEntity.createdAt())
                .updatedAt(eventEntity.updatedAt())
                .creatorId(eventEntity.creatorId())
                .build();

        return eventRepository.updateEvent(patchedEvent);
    }

    @Override
    public void deleteEvent(@NonNull Long eventId) {
        eventRepository.deleteEvent(eventId);
    }

}
