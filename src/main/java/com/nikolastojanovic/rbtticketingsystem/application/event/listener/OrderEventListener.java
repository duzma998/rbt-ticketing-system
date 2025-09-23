package com.nikolastojanovic.rbtticketingsystem.application.event.listener;

import com.nikolastojanovic.rbtticketingsystem.application.model.request.ApplicationOrderRequest;
import com.nikolastojanovic.rbtticketingsystem.application.service.ApplicationOrderService;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.OrderMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final ApplicationOrderService orderService;

    @Value("${kafka.order.principal}")
    private String technicalPrincipalUser;

    @KafkaListener(topics = "${kafka.order.topic}", groupId = "${kafka.order.group-id}")
    public void listen(ApplicationOrderRequest request) {
        log.info("Received order request: {}", request);
        orderService.create(request, technicalPrincipalUser, OrderMethod.KAFKA);
    }
}