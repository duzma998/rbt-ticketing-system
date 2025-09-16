package com.nikolastojanovic.rbtticketingsystem.application.event.listener;

import com.nikolastojanovic.rbtticketingsystem.application.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(TicketEventListener.class);

    private final TicketService ticketService;

    @KafkaListener(topics = "ticket-created", groupId = "ticket-service-group")
    public void handleTicketCreated(@Payload String ticketData,
                                    @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                    @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                    @Header(KafkaHeaders.OFFSET) long offset,
                                    Acknowledgment acknowledgment) {
        try {
            logger.info("Received ticket created event: {}", ticketData);
            logger.info("Topic: {}, Partition: {}, Offset: {}", topic, partition, offset);

            logger.info(ticketService.createTicket(ticketData));

            acknowledgment.acknowledge();

        } catch (Exception e) {
            logger.error("Error processing ticket created event: {}", e.getMessage(), e);
        }
    }
}
