package com.nikolastojanovic.rbtticketingsystem.domain.in;

import com.nikolastojanovic.rbtticketingsystem.domain.model.Order;
import com.nikolastojanovic.rbtticketingsystem.domain.model.request.OrderRequest;
import lombok.NonNull;

public interface OrderService {
    Order createOrder(@NonNull OrderRequest request);
}
