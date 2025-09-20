package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.mapper;

import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import com.nikolastojanovic.rbtticketingsystem.domain.model.User;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.EventStatus;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.EventEntity;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.UserEntity;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.enums.InfraEventStatus;
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
                entity.getCreatedBy() != null ? entity.getCreatedBy().getUsername() : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getCreatedBy().getId()
        );
    }

    public EventEntity toEntity(@NotNull Event event, @NotNull UserEntity creator) {
            return EventEntity.builder()
                    .id(event.id())
                    .name(event.name())
                    .description(event.description())
                    .eventType(event.eventType())
                    .venueName(event.venueName())
                    .venueAddress(event.venueAddress())
                    .eventDate(event.eventDate())
                    .totalTickets(event.totalTickets())
                    .availableTickets(event.availableTickets())
                    .maxTicketsPerPurchase(event.maxTicketsPerPurchase())
                    .ticketPrice(event.ticketPrice())
                    .status(mapToInfraEventStatus(event.status()))
                    .createdBy(creator)
                    .createdAt(event.createdAt())
                    .updatedAt(event.updatedAt())
                    .build();
    }

    private InfraEventStatus mapToInfraEventStatus(EventStatus status) {
        return switch (status) {
            case ACTIVE -> InfraEventStatus.ACTIVE;
            case FINISHED -> InfraEventStatus.FINISHED;
            case CANCELLED -> InfraEventStatus.CANCELLED;

        };
    }

    EventStatus mapFromInfraEventStatus(InfraEventStatus status) {
        return switch (status) {
            case ACTIVE -> EventStatus.ACTIVE;
            case FINISHED -> EventStatus.FINISHED;
            case CANCELLED -> EventStatus.CANCELLED;
        };
    }

}
