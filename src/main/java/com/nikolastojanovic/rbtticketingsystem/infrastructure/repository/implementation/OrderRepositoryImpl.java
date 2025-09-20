package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.implementation;

import com.nikolastojanovic.rbtticketingsystem.domain.model.Order;
import com.nikolastojanovic.rbtticketingsystem.domain.out.repository.OrderRepository;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa.EventRepositoryJpa;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa.OrderRepositoryJpa;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.jpa.UserRepositoryJpa;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.mapper.OrderRepositoryMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderRepositoryJpa orderRepositoryJpa;
    private final UserRepositoryJpa userRepositoryJpa;
    private final EventRepositoryJpa eventRepositoryJpa;
    private final OrderRepositoryMapper orderMapper;

    @Override
    public Order saveOrder(@NonNull Order order) {
        var user = userRepositoryJpa.findById(order.userId()).orElseThrow();
        var event = eventRepositoryJpa.findById(order.eventId()).orElseThrow();
        var entity = orderMapper.toEntity(order);
        entity.setEvent(event);
        entity.setUser(user);

        return orderMapper.toDomain(orderRepositoryJpa.save(entity));
    }
}
