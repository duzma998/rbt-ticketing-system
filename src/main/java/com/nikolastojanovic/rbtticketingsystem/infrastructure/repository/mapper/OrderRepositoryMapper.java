package com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.mapper;

import com.nikolastojanovic.rbtticketingsystem.domain.model.Order;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.OrderMethod;
import com.nikolastojanovic.rbtticketingsystem.domain.model.enums.OrderStatus;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.entity.OrderEntity;

import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.enums.InfraOrderMethod;
import com.nikolastojanovic.rbtticketingsystem.infrastructure.repository.enums.InfraOrderStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderRepositoryMapper {

    public OrderEntity toEntity(Order order) {
        return OrderEntity.builder()
                .ticketCount(order.ticketCount())
                .totalAmount(order.totalAmount())
                .status(InfraOrderStatus.valueOf(order.status().name()))
                .orderMethod(InfraOrderMethod.valueOf(order.orderMethod().name()))
                .createdAt(order.createdAt())
                .updatedAt(order.updatedAt())
                .build();
    }

    public Order toDomain(OrderEntity entity) {
        return Order.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .eventId(entity.getEvent().getId())
                .ticketCount(entity.getTicketCount())
                .totalAmount(entity.getTotalAmount())
                .status(OrderStatus.valueOf(entity.getStatus().name()))
                .orderMethod(OrderMethod.valueOf(entity.getOrderMethod().name()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
