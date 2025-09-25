package com.nikolastojanovic.rbtticketingsystem.domain.model;

import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.OrderMethod;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
public record Order(
        Long id,
        Long userId,
        Long eventId,
        Integer ticketCount,
        BigDecimal totalAmount,
        OrderStatus status,
        OrderMethod orderMethod,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt
) {}
