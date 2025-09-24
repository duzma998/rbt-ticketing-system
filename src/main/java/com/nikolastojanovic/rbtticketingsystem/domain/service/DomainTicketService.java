package com.nikolastojanovic.rbtticketingsystem.domain.service;

import com.nikolastojanovic.rbtticketingsystem.domain.exception.TicketingException;
import com.nikolastojanovic.rbtticketingsystem.domain.exception.Error;
import com.nikolastojanovic.rbtticketingsystem.domain.in.TicketService;
import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import com.nikolastojanovic.rbtticketingsystem.domain.model.Ticket;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.TicketStatus;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.EventRepository;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.TicketRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;

import static com.nikolastojanovic.rbtticketingsystem.domain.util.CodeGenerator.generateTicketCode;

@RequiredArgsConstructor
@Service
public class DomainTicketService implements TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    @Override
    public void initializeTicketsForEvent(@NonNull Event event) {
        var tickets = new ArrayList<Ticket>();
        for (int i = 0; i < event.totalTickets(); i++) {
            // todo: Add latter to seat number...
            tickets.add(createNewTicket(event, String.valueOf(i + 1)));
        }
        ticketRepository.saveTickets(tickets,  event.id(), event.creatorId());
    }

    @Override
    public void reserveTicket(@NonNull Long eventId, @NonNull Long orderId, String seatNumber) {
        Ticket ticket;
        if (seatNumber != null && !seatNumber.isBlank()) {
            ticket = ticketRepository.getByEventIdAndSeat(eventId, seatNumber).orElseThrow(
                    () -> new TicketingException(Error.NOT_FOUND, "Seat number (" + seatNumber + ") not found."));
        } else {
            var tickets = ticketRepository.getByEventId(eventId);
            ticket = tickets.stream().filter(t -> t.status() == TicketStatus.CREATED).findFirst().orElseThrow(
                    () -> new TicketingException(Error.NOT_FOUND, "Ticket are not available."));
        }
        var updatedTicket = ticket.withOrderId(orderId);
        updatedTicket = updatedTicket.withStatus(TicketStatus.RESERVED);
        ticketRepository.saveTicket(updatedTicket);
    }

    @Override
    public boolean isSeatAvailable(@NonNull Long eventId, @NonNull String seat) {
        return ticketRepository.isSeatAvailable(eventId, seat);
    }

    @Override
    public boolean isTicketValid(String ticketCode) {

        var ticket = ticketRepository.getByTicketCode(ticketCode).orElseThrow(()-> new TicketingException(Error.NOT_FOUND, "Ticket not found."));
        var event = eventRepository.getEvent(ticket.eventId());

        return ticket.status() == TicketStatus.RESERVED && event.eventDate().isAfter(ZonedDateTime.now());
    }

    @Override
    public void cancelTicket(@NonNull String ticketCode) {
        var ticket = ticketRepository.getByTicketCode(ticketCode).orElseThrow(()-> new TicketingException(Error.NOT_FOUND, "Ticket not found."));
        var event = eventRepository.getEvent(ticket.eventId());

        if(ticket.status() == TicketStatus.USED) {
            throw new TicketingException(Error.BAD_REQUEST, "Ticket has already been used.");
        }

        if(ticket.status() == TicketStatus.CANCELLED) {
            throw new TicketingException(Error.BAD_REQUEST, "Ticket has already been cancelled.");
        }

        ticketRepository.saveTicket(ticket.withStatus(TicketStatus.CANCELLED));

        var listOfTicket = new ArrayList<Ticket>();
        listOfTicket.add(createNewTicket(event, ticket.seatNumber()));

        ticketRepository.saveTickets(listOfTicket, ticket.eventId(), ticket.userId());

        eventRepository.updateAvailableTickets(ticket.eventId(), event.availableTickets() + 1);
    }

    private Ticket createNewTicket(Event event, String seatNumber) {
        return Ticket.builder()
                .eventId(event.id())
                .userId(event.creatorId())
                .ticketCode(generateTicketCode())
                .status(TicketStatus.CREATED)
                .updatedAt(ZonedDateTime.now())
                .createdAt(ZonedDateTime.now())
                .seatNumber(seatNumber)
                .build();
    }
}
