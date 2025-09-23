package com.nikolastojanovic.rbtticketingsystem.application.service;

import com.nikolastojanovic.rbtticketingsystem.application.mapper.ApplicationOrderMapper;
import com.nikolastojanovic.rbtticketingsystem.application.model.request.ApplicationOrderRequest;
import com.nikolastojanovic.rbtticketingsystem.application.model.response.OrderResponse;
import com.nikolastojanovic.rbtticketingsystem.domain.in.OrderService;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.OrderMethod;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationOrderService {

    private final OrderService orderService;
    private final ApplicationOrderMapper orderMapper;

    @Transactional
    public OrderResponse create(@NonNull ApplicationOrderRequest request, @NonNull String principal, @NonNull OrderMethod orderMethod) {
        return orderMapper.toResponse(orderService.createOrder(orderMapper.toRequest(request, principal, orderMethod)));
    }

}
