package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.implementation;

import com.nikolastojanovic.rbtticketingsystem.domain.exception.CustomException;
import com.nikolastojanovic.rbtticketingsystem.domain.exception.Error;
import com.nikolastojanovic.rbtticketingsystem.domain.model.Ticket;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.TicketRepository;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa.EventRepositoryJpa;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa.OrderRepositoryJpa;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa.TicketRepositoryJpa;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa.UserRepositoryJpa;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.mapper.TicketRepositoryMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {

    private final TicketRepositoryJpa ticketRepositoryJpa;
    private final EventRepositoryJpa eventRepositoryJpa;
    private final UserRepositoryJpa userRepositoryJpa;
    private final TicketRepositoryMapper ticketMapper;
    private final OrderRepositoryJpa orderRepositoryJpa;

    @Override
    public void saveTickets(@NonNull List<Ticket> tickets, @NonNull Long userId, @NonNull Long eventId) {
        var event = eventRepositoryJpa.findById(eventId).orElseThrow(() -> new CustomException(Error.NOT_FOUND, "Event ("+eventId+") not found on ticket generation."));
        var user = userRepositoryJpa.findById(userId).orElseThrow(() -> new CustomException(Error.NOT_FOUND, "User ("+userId+") not found on ticket generation."));

        var entities = tickets.stream().map(e -> {
            var entity = ticketMapper.toEntity(e);
            entity.setUser(user);
            entity.setEvent(event);

            return entity;
        }).toList();

        ticketRepositoryJpa.saveAll(entities);
    }

    @Override
    public Optional<Ticket> getByEvetIdAndSeat(@NonNull Long eventId, @NonNull String seatNumber) {
        return ticketRepositoryJpa.findByEventIdAndSeatNumber(eventId, seatNumber).map(ticketMapper::toDomain);
    }

    @Override
    public List<Ticket> getByEvetId(@NonNull Long eventId) {
        return ticketRepositoryJpa.findByEventId(eventId).stream().map(ticketMapper::toDomain).toList();
    }

    @Override
    public void saveTicket(@NonNull Ticket updatedTicket) {

        var event = eventRepositoryJpa.findById(updatedTicket.eventId()).orElseThrow(() -> new CustomException(Error.NOT_FOUND, "Event ("+updatedTicket.eventId()+") not found on ticket update."));
        var user = userRepositoryJpa.findById(updatedTicket.userId()).orElseThrow(() -> new CustomException(Error.NOT_FOUND, "User ("+updatedTicket.userId()+") not found on ticket update."));
        var order = orderRepositoryJpa.findById(updatedTicket.orderId()).orElseThrow(() -> new CustomException(Error.NOT_FOUND, "Order ("+updatedTicket.orderId()+") not found on ticket update."));

        var entity = ticketMapper.toEntity(updatedTicket);
        entity.setUser(user);
        entity.setEvent(event);
        entity.setOrder(order);

        ticketRepositoryJpa.save(entity);
    }
}
