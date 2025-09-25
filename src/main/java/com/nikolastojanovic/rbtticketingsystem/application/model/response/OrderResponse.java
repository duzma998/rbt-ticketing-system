package com.nikolastojanovic.rbtticketingsystem.application.model.response;

import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.OrderStatus;

import java.math.BigDecimal;

public record OrderResponse(
        Long id,
        Long userId,
        Long eventId,
        Integer ticketCount,
        BigDecimal totalAmount,
        OrderStatus status
) {
}
