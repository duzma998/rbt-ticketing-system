package com.nikolastojanovic.rbtticketingsystem.application.model.response;

import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.OrderMethod;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.OrderStatus;

import java.time.ZonedDateTime;

public record OrderResponse(
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
