package com.nikolastojanovic.rbtticketingsystem.domain.in;

import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import lombok.NonNull;

public interface TicketService {

    void initializeTicketsForEvent(@NonNull Event event);

    void reserveTicket(@NonNull Long eventId, @NonNull Long orderId, String seatNumber);

    boolean isSeatAvailable(@NonNull Long eventId,@NonNull String seat);

    boolean isTicketValid(String ticketCode);

    void cancelTicket(@NonNull String ticketCode);
}
