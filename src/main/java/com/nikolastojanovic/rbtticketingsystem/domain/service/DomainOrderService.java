package com.nikolastojanovic.rbtticketingsystem.domain.service;

import com.nikolastojanovic.rbtticketingsystem.domain.exception.Error;
import com.nikolastojanovic.rbtticketingsystem.domain.exception.TicketingException;
import com.nikolastojanovic.rbtticketingsystem.domain.in.OrderService;
import com.nikolastojanovic.rbtticketingsystem.domain.in.TicketService;
import com.nikolastojanovic.rbtticketingsystem.domain.model.Event;
import com.nikolastojanovic.rbtticketingsystem.domain.model.Order;
import com.nikolastojanovic.rbtticketingsystem.domain.model.User;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.OrderStatus;
import com.nikolastojanovic.rbtticketingsystem.domain.model.request.OrderRequest;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.EventRepository;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.OrderRepository;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

        eventRepository.updateAvailableTickets(request.eventId(),
                event.availableTickets() - request.ticketCount());

        final var savedOrder = orderRepository.saveOrder(order);
        if (request.seats() != null) {
            request.seats().forEach(s -> ticketService.reserveTicket(request.eventId(), savedOrder.id(), s));
        }
        var remainTickets = request.seats() != null ? request.ticketCount() - request.seats().size() : request.ticketCount();
        for (int i = 0; i < remainTickets; i++) {
            ticketService.reserveTicket(request.eventId(), savedOrder.id(), null);
        }

        return order;
    }

    private Order createNewOrder(OrderRequest request, User user, Event event) {
        return Order.builder()
                .userId(user.Id())
                .eventId(event.id())
                .ticketCount(request.ticketCount())
                .totalAmount(event.ticketPrice() * request.ticketCount())
                .status(OrderStatus.ACTIVE)
                .orderMethod(request.orderMethod())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();
    }
}
