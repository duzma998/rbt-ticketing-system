package com.nikolastojanovic.rbtticketingsystem.domain.service;

import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.EventEntity;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.TicketEntity;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.enums.InfraEventStatus;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.enums.InfraTicketStatus;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa.EventRepositoryJpa;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa.TicketRepositoryJpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventSchedulerService {

    private final EventRepositoryJpa eventRepository;
    private final TicketRepositoryJpa ticketRepository;

    @Scheduled(cron = "30 * * * * *")
    @SchedulerLock(name = "expireEventsAndTickets", lockAtMostFor = "50m", lockAtLeastFor = "5m")
    @Transactional
    public void expireEventsAndTickets() {
        log.info("Starting scheduled job to expire events and tickets");

        try {
            ZonedDateTime now = ZonedDateTime.now();

            List<EventEntity> expiredEvents = eventRepository.findAllByStatusAndEventDateBefore(InfraEventStatus.ACTIVE, now );

            log.info("Found {} expired events to process", expiredEvents.size());

            int totalExpiredTickets = 0;

            for (EventEntity event : expiredEvents) {
                log.info("Processing expired event: {} (ID: {}, Event Date: {})",
                        event.getName(), event.getId(), event.getEventDate());

                event.setStatus(InfraEventStatus.FINISHED);
                eventRepository.save(event);

                List<InfraTicketStatus> ticketStatuses = List.of(InfraTicketStatus.CREATED, InfraTicketStatus.RESERVED);

                List<TicketEntity> activeTickets = ticketRepository.findByEventIdAndStatusIn(
                        event.getId(), ticketStatuses);

                if (!activeTickets.isEmpty()) {
                    log.info("Expiring {} active tickets for event: {}",
                            activeTickets.size(), event.getName());

                    for (TicketEntity ticket : activeTickets) {
                        ticket.setStatus(InfraTicketStatus.EXPIRED);
                        ticketRepository.save(ticket);
                    }

                    totalExpiredTickets += activeTickets.size();
                }
            }

            log.info("Scheduled job completed successfully. " +
                    "Expired {} events and {} tickets", expiredEvents.size(), totalExpiredTickets);

        } catch (Exception e) {
            log.error("Error occurred during event expiration job", e);
            throw e;
        }
    }
}