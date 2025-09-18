package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.mapper;

import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.EventStatus;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.EventEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class EventRepositoryMapper {
    public Event toDomain(@NotNull EventEntity entity) {

        return new Event(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getEventType(),
                entity.getVenueName(),
                entity.getVenueAddress(),
                entity.getEventDate(),
                entity.getTotalTickets(),
                entity.getAvailableTickets(),
                entity.getMaxTicketsPerPurchase(),
                entity.getTicketPrice(),
                EventStatus.valueOf(entity.getStatus().name()),
                entity.getCreatedBy() != null ? entity.getCreatedBy().getId() : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                null
        );
    }

}
