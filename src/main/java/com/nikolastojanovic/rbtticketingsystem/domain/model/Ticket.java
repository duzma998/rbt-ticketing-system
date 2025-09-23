package com.nikolastojanovic.rbtticketingsystem.domain.model;

import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.TicketStatus;
import lombok.Builder;
import lombok.With;

import java.time.ZonedDateTime;

@Builder
@With
public record Ticket(
        Long id,
        Long orderId,
        Long eventId,
        Long userId,
        String ticketCode,
        String seatNumber,
        TicketStatus status,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt
) {}
