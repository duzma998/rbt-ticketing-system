package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.mapper;

import com.nikolastojanovic.rbtticketingsystem.domain.model.Ticket;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.TicketStatus;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.EventEntity;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.OrderEntity;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.TicketEntity;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.UserEntity;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.enums.InfraTicketStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TicketRepositoryMapper {

    public TicketEntity toEntity(@NotNull Ticket ticket) {
        return TicketEntity.builder()
                .id(ticket.id())
                .ticketCode(ticket.ticketCode())
                .status(mapToInfraStatus(ticket.status()))
                .seatNumber(ticket.seatNumber())
                .createdAt(ticket.createdAt())
                .updatedAt(ticket.updatedAt())
                .build();
    }

    public Ticket toDomain(@NotNull TicketEntity ticketEntity) {
        return Ticket.builder()
                .id(ticketEntity.getId())
                .eventId(extractEventId(ticketEntity))
                .userId(extractUserId(ticketEntity))
                .status(mapToDomainStatus(ticketEntity.getStatus()))
                .orderId(extractOrderId(ticketEntity))
                .ticketCode(ticketEntity.getTicketCode())
                .seatNumber(ticketEntity.getSeatNumber())
                .createdAt(ticketEntity.getCreatedAt())
                .updatedAt(ticketEntity.getUpdatedAt())
                .build();
    }

    private TicketStatus mapToDomainStatus(InfraTicketStatus status) {
        return Optional.ofNullable(status)
                .map(s -> TicketStatus.valueOf(s.name()))
                .orElse(null);
    }

    private InfraTicketStatus mapToInfraStatus(TicketStatus status) {
        return Optional.ofNullable(status)
                .map(s -> InfraTicketStatus.valueOf(s.name()))
                .orElse(null);
    }

    private Long extractEventId(TicketEntity ticketEntity) {
        return Optional.ofNullable(ticketEntity.getEvent())
                .map(EventEntity::getId)
                .orElse(null);
    }

    private Long extractUserId(TicketEntity ticketEntity) {
        return Optional.ofNullable(ticketEntity.getUser())
                .map(UserEntity::getId)
                .orElse(null);
    }

    private Long extractOrderId(TicketEntity ticketEntity) {
        return Optional.ofNullable(ticketEntity.getOrder())
                .map(OrderEntity::getId)
                .orElse(null);
    }
}