package com.nikolastojanovic.rbtticketingsystem.application.service;

import com.nikolastojanovic.rbtticketingsystem.application.mapper.ApplicationOrderMapper;
import com.nikolastojanovic.rbtticketingsystem.application.model.request.ApplicationOrderRequest;
import com.nikolastojanovic.rbtticketingsystem.application.model.response.OrderResponse;
import com.nikolastojanovic.rbtticketingsystem.domain.in.OrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ApplicationOrderService {

    private final OrderService orderService;
    private final ApplicationOrderMapper orderMapper;

    @Transactional
    public OrderResponse create(@NonNull ApplicationOrderRequest request,@NonNull Principal principal) {
     return orderMapper.toResponse(orderService.createOrder(orderMapper.toRequest(request, principal)));
    }

}
