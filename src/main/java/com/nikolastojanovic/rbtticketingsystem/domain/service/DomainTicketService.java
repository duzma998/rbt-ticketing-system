package com.nikolastojanovic.rbtticketingsystem.domain.service;

import com.nikolastojanovic.rbtticketingsystem.domain.exception.CustomException;
import com.nikolastojanovic.rbtticketingsystem.domain.exception.Error;
import com.nikolastojanovic.rbtticketingsystem.domain.in.TicketService;
import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import com.nikolastojanovic.rbtticketingsystem.domain.model.Ticket;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.TicketStatus;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.TicketRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.nikolastojanovic.rbtticketingsystem.domain.util.CodeGenerator.generateTicketCode;

@RequiredArgsConstructor
public class DomainTicketService implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public void initializeTicketsForEvent(@NonNull Event event) {
        var tickets = new ArrayList<Ticket>();
        for (int i = 0; i < event.totalTickets(); i++) {
            // todo: Add latter to seat number...
            tickets.add(createNewTicket(event, String.valueOf(i + 1)));
        }
        ticketRepository.saveTickets(tickets, event.creator().Id(), event.id());
    }

    @Override
    public void reserveTicket(@NonNull Long eventId, @NonNull Long orderId, String seatNumber) {
        Optional<Ticket> ticket = null;
        if (seatNumber != null && !seatNumber.isBlank()) {
            ticket = ticketRepository.getByEvetIdAndSeat(eventId, seatNumber);
        } else {
            ticket = ticketRepository.getByEvetId(eventId);
        }
        // todo custom exception
        var updatedTicket = ticket.map(t -> t.withStatus(TicketStatus.RESERVED).withOrderId(orderId)).orElseThrow(() -> new CustomException(Error.NOT_FOUND, "Ticket for event ("+eventId+") not found"));
        ticketRepository.saveTicket(updatedTicket);
    }

    private Ticket createNewTicket(Event event, String seatNumber) {
        return Ticket.builder()
                .eventId(event.id())
                .userId(event.creator().Id()) // todo
                .ticketCode(generateTicketCode())
                .status(TicketStatus.CREATED)
                .updatedAt(ZonedDateTime.now())
                .createdAt(ZonedDateTime.now())
                .seatNumber(seatNumber)
                .build();
    }
}
