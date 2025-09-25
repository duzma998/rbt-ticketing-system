package com.nikolastojanovic.rbtticketingsystem.domain.in;

import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import com.nikolastojanovic.rbtticketingsystem.domain.model.Ticket;
import lombok.NonNull;

import java.util.List;

public interface TicketService {

    void initializeTicketsForEvent(@NonNull Event event);

    void reserveTicket(@NonNull Long eventId, @NonNull Long orderId, String seatNumber);

    boolean isSeatAvailable(@NonNull Long eventId,@NonNull String seat);

    boolean isTicketValid(String ticketCode);

    void cancelTicket(@NonNull String ticketCode);

    List<Ticket> getTicketsByEventId(@NonNull Long eventId);

    void useTicket(@NonNull String ticketCode);
}
