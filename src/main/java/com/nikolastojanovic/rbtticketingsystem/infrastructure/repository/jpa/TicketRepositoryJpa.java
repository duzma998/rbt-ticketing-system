package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa;

import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.TicketEntity;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.enums.InfraTicketStatus;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepositoryJpa extends JpaRepository<TicketEntity, Long> {
    List<TicketEntity> findByEventId(Long eventId);

    Optional<TicketEntity> findByEventIdAndSeatNumber(Long eventId, String seatNumber);

    List<TicketEntity> findByEventIdAndStatusIn(@NonNull Long eventId, @NonNull List<InfraTicketStatus> status);

    Boolean existsByEventIdAndSeatNumberAndStatus(@NonNull Long eventId, @NonNull String seatNumber, InfraTicketStatus status);

    Optional<TicketEntity> findByTicketCode(@NonNull String ticketCode);
}
