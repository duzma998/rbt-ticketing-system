package com.nikolastojanovic.rbtticketingsystem.application.controller.rest;

import com.nikolastojanovic.rbtticketingsystem.application.model.request.ApplicationOrderRequest;
import com.nikolastojanovic.rbtticketingsystem.application.model.response.OrderResponse;
import com.nikolastojanovic.rbtticketingsystem.application.service.ApplicationOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final ApplicationOrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody ApplicationOrderRequest request, @AuthenticationPrincipal Principal principal) {
       return ResponseEntity.ok(orderService.create(request, principal));
    }
}
