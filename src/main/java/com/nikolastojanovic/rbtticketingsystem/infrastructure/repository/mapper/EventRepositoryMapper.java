package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.mapper;

import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.EventStatus;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.EventEntity;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.UserEntity;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.enums.InfraEventStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EventRepositoryMapper {

    public Event toDomain(@NotNull EventEntity entity) {
        return Event.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .eventType(entity.getEventType())
                .venueName(entity.getVenueName())
                .venueAddress(entity.getVenueAddress())
                .eventDate(entity.getEventDate())
                .totalTickets(entity.getTotalTickets())
                .availableTickets(entity.getAvailableTickets())
                .maxTicketsPerPurchase(entity.getMaxTicketsPerPurchase())
                .ticketPrice(entity.getTicketPrice())
                .status(mapToDomainStatus(entity.getStatus()))
                .createdBy(extractUsername(entity.getCreatedBy()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .creatorId(extractUserId(entity.getCreatedBy()))
                .build();
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
                .status(mapToInfraStatus(event.status()))
                .createdBy(creator)
                .createdAt(event.createdAt())
                .updatedAt(event.updatedAt())
                .build();
    }

    private EventStatus mapToDomainStatus(InfraEventStatus status) {
        return Optional.ofNullable(status)
                .map(s -> EventStatus.valueOf(s.name()))
                .orElse(null);
    }

    private InfraEventStatus mapToInfraStatus(EventStatus status) {
        return Optional.ofNullable(status)
                .map(s -> InfraEventStatus.valueOf(s.name()))
                .orElse(null);
    }

    private String extractUsername(UserEntity user) {
        return Optional.ofNullable(user)
                .map(UserEntity::getUsername)
                .orElse(null);
    }

    private Long extractUserId(UserEntity user) {
        return Optional.ofNullable(user)
                .map(UserEntity::getId)
                .orElse(null);
    }
}