package com.nikolastojanovic.rbtticketingsystem.domain.out.repository;

import com.nikolastojanovic.rbtticketingsystem.domain.model.Ticket;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    void saveTickets(@NonNull List<Ticket> tickets,@NonNull Long eventId,@NonNull Long userId);

    Optional<Ticket> getByEventIdAndSeat(@NonNull Long eventId, @NonNull String seatNumber);

    List<Ticket> getByEventId(@NonNull Long eventId);

    void saveTicket(@NonNull Ticket updatedTicket);

    boolean isSeatAvailable(Long eventId, String seat);

    Optional<Ticket> getByTicketCode(@NonNull String ticketCode);
}
