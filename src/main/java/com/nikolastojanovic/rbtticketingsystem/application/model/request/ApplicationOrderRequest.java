package com.nikolastojanovic.rbtticketingsystem.application.model.request;

import java.util.List;

public record ApplicationOrderRequest(
        Long eventId,
        List<String> seats,
        Integer ticketCount
) {
}
