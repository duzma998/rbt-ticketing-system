package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.mapper;

import com.nikolastojanovic.rbtticketingsystem.domain.model.Ticket;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.TicketStatus;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.TicketEntity;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.enums.InfraTicketStatus;
import org.springframework.stereotype.Component;

@Component
public class TicketRepositoryMapper {
    public TicketEntity toEntity(Ticket ticket) {
        return TicketEntity.builder()
                .id(ticket.id())
                .ticketCode(ticket.ticketCode())
                .status(InfraTicketStatus.valueOf(ticket.status().name()))
                .seatNumber(ticket.seatNumber())
                .createdAt(ticket.createdAt())
                .updatedAt(ticket.updatedAt())
                .build();
    }

    public Ticket toDomain(TicketEntity ticketEntity) {
        return Ticket.builder()
                .id(ticketEntity.getId())
                .eventId(ticketEntity.getEvent().getId())
                .userId(ticketEntity.getUser().getId())
                .status(TicketStatus.valueOf(ticketEntity.getStatus().name()))
                .orderId(ticketEntity.getOrder() != null ? ticketEntity.getOrder().getId() : null)
                .ticketCode(ticketEntity.getTicketCode())
                .seatNumber(ticketEntity.getSeatNumber())
                .createdAt(ticketEntity.getCreatedAt())
                .updatedAt(ticketEntity.getUpdatedAt())
                .build();
    }
}
