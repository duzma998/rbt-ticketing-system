package com.nikolastojanovic.rbtticketingsystem.application.mapper;

import com.nikolastojanovic.rbtticketingsystem.application.model.request.ApplicationOrderRequest;
import com.nikolastojanovic.rbtticketingsystem.application.model.response.OrderResponse;
import com.nikolastojanovic.rbtticketingsystem.domain.model.Order;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.OrderMethod;
import com.nikolastojanovic.rbtticketingsystem.domain.model.request.OrderRequest;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class ApplicationOrderMapper {
    public OrderResponse toResponse(Order order) {
        return null;
    }

    public OrderRequest toRequest(ApplicationOrderRequest request, Principal principal) {
        return new OrderRequest(principal.getName(), request.eventId(), request.seats(), OrderMethod.API, request.ticketCount());
    }
}
