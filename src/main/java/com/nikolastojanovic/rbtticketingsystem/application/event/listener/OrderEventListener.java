package com.nikolastojanovic.rbtticketingsystem.application.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikolastojanovic.rbtticketingsystem.application.model.request.ApplicationOrderRequest;
import com.nikolastojanovic.rbtticketingsystem.application.service.ApplicationOrderService;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.OrderMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final ApplicationOrderService orderService;

    @KafkaListener(topics = "RESERVE_TICKETS", groupId = "rbt-ticketing-system")
    public void listen(String message) throws JsonProcessingException {
        // todo: Receive application order request instead of string order message
        ObjectMapper mapper = new ObjectMapper();
        ApplicationOrderRequest request = mapper.readValue(message, ApplicationOrderRequest.class);

        orderService.create(request, "nikola1234", OrderMethod.KAFKA);
    }
}
