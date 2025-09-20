package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa;

import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepositoryJpa extends JpaRepository<TicketEntity, Long> {
    Optional<TicketEntity> findByEventId(Long eventId);

    Optional<TicketEntity> findByEventIdAndSeatNumber(Long eventId, String seatNumber);
}
