package com.nikolastojanovic.rbtticketingsystem.domain.model;

import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.OrderMethod;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.OrderStatus;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record Order(
        Long id,
        Long userId,
        Long eventId,
        Integer ticketCount,
        Double totalAmount,
        OrderStatus status,
        OrderMethod orderMethod,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt
) {
}
