package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa;

import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.EventEntity;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.enums.InfraEventStatus;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

public interface EventRepositoryJpa extends JpaRepository<EventEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE EventEntity e SET e.availableTickets = :availableTickets WHERE e.id = :eventId")
    void updateAvailableTickets(@NonNull @Param("eventId") Long eventId, @NonNull @Param("availableTickets") Integer availableTickets);

    List<EventEntity> findAllByStatusAndCreatedAtBefore(InfraEventStatus status, ZonedDateTime now);
}
