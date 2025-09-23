package com.nikolastojanovic.rbtticketingsystem.application.controller.rest;

import com.nikolastojanovic.rbtticketingsystem.application.model.request.ApplicationOrderRequest;
import com.nikolastojanovic.rbtticketingsystem.application.model.response.OrderResponse;
import com.nikolastojanovic.rbtticketingsystem.application.service.ApplicationOrderService;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.OrderMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final ApplicationOrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody ApplicationOrderRequest request,
            @AuthenticationPrincipal String principal
    ) {
        return ResponseEntity.ok(orderService.create(request, principal, OrderMethod.API));
    }
}
