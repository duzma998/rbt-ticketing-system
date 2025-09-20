package com.nikolastojanovic.rbtticketingsystem.domain.model.request;

import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.OrderMethod;

import java.util.List;

public record OrderRequest(
        String username,
        Long eventId,
        List<String> seats,
        OrderMethod orderMethod,
        Integer ticketCount
) {
}
