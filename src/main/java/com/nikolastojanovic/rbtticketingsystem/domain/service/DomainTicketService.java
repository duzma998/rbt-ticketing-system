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
        ticketRepository.saveTickets(tickets, event.creatorId(), event.id());
    }

    @Override
    public void reserveTicket(@NonNull Long eventId, @NonNull Long orderId, String seatNumber) {
        Ticket ticket;
        if (seatNumber != null && !seatNumber.isBlank()) {
            // todo custom exeption for seat number or ticket
            ticket = ticketRepository.getByEvetIdAndSeat(eventId, seatNumber).orElseThrow(() -> new CustomException(Error.NOT_FOUND, "Ticket are not available."));
        } else {
            var tickets = ticketRepository.getByEvetId(eventId);
            ticket = tickets.stream().filter(t -> t.status() == TicketStatus.CREATED).findFirst().orElseThrow(() -> new CustomException(Error.NOT_FOUND, "Ticket are not available."));
        }
        // todo custom exception
        var updatedTicket = ticket.withOrderId(orderId);
        updatedTicket = updatedTicket.withStatus(TicketStatus.RESERVED);
        ticketRepository.saveTicket(updatedTicket);
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
