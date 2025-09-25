package com.nikolastojanovic.rbtticketingsystem.domain.service;

import com.nikolastojanovic.rbtticketingsystem.domain.exception.Error;
import com.nikolastojanovic.rbtticketingsystem.domain.exception.TicketingException;
import com.nikolastojanovic.rbtticketingsystem.domain.in.OrderService;
import com.nikolastojanovic.rbtticketingsystem.domain.in.TicketService;
import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import com.nikolastojanovic.rbtticketingsystem.domain.model.Order;
import com.nikolastojanovic.rbtticketingsystem.domain.model.User;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.EventStatus;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.OrderStatus;
import com.nikolastojanovic.rbtticketingsystem.domain.model.request.OrderRequest;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.EventRepository;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.OrderRepository;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class DomainOrderService implements OrderService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final OrderRepository orderRepository;
    private final TicketService ticketService;

    @Override
    public Order createOrder(@NonNull OrderRequest request) {
        var user = userRepository.getByUsernameOrThrow(request.username());
        var event = eventRepository.getEvent(request.eventId());
        var order = createNewOrder(request, user, event);

        if(event.status() == EventStatus.CANCELLED) {
            throw new TicketingException(Error.BAD_REQUEST, "Event has been cancelled.");
        }

        if(event.eventDate().isBefore(ZonedDateTime.now())) {
            throw new TicketingException(Error.BAD_REQUEST, "Event has already passed.");
        }

        if (event.availableTickets() < request.ticketCount()) {
            throw new TicketingException(Error.BAD_REQUEST, "Not enough available tickets.");
        }

        if (request.seats() != null && request.seats().size() > request.ticketCount()) {
            throw new TicketingException(Error.BAD_REQUEST, "Too many seats.");
        }

        if (request.ticketCount() > event.maxTicketsPerPurchase()) {
            throw new TicketingException(Error.BAD_REQUEST, "Too many tickets.");
        }

        final var savedOrder = orderRepository.saveOrder(order);
        System.out.println(request.seats());
        if (request.seats() != null) {

            for (var seat : request.seats()) {
                if (!ticketService.isSeatAvailable(request.eventId(), seat)) {
                    throw new TicketingException(Error.BAD_REQUEST, "Ticket for seat " + seat + " is already reserved.");
                }
            }

            request.seats().forEach(s -> ticketService.reserveTicket(request.eventId(), savedOrder.id(), s));
        }

        var remainTickets = request.seats() != null ? request.ticketCount() - request.seats().size() : request.ticketCount();

        for (int i = 0; i < remainTickets; i++) {
            ticketService.reserveTicket(request.eventId(), savedOrder.id(), null);
        }

        eventRepository.updateAvailableTickets(request.eventId(),
                event.availableTickets() - request.ticketCount());

        return savedOrder;
    }

    private Order createNewOrder(OrderRequest request, User user, Event event) {
        return Order.builder()
                .userId(user.Id())
                .eventId(event.id())
                .ticketCount(request.ticketCount())
                .totalAmount(event.ticketPrice().multiply(BigDecimal.valueOf(request.ticketCount())))
                .status(OrderStatus.ACTIVE)
                .orderMethod(request.orderMethod())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();
    }
}
