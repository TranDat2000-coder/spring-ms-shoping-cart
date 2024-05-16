package com.example.orderservice.service.impl;

import com.example.orderservice.request.OrderRequest;
import com.example.orderservice.response.OrderResponse;
import com.example.orderservice.service.IOrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService {

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        return 0;
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        return null;
    }
}
