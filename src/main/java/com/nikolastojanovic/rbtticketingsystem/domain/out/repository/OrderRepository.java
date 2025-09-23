package com.nikolastojanovic.rbtticketingsystem.domain.out.repository;

import com.nikolastojanovic.rbtticketingsystem.domain.model.Order;
import lombok.NonNull;

public interface OrderRepository {

    Order saveOrder(@NonNull Order order);
}
