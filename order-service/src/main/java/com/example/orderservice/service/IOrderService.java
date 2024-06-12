package com.example.orderservice.service;

import com.example.orderservice.request.OrderRequest;
import com.example.orderservice.response.OrderResponse;

public interface IOrderService {

    long createOrder(OrderRequest orderRequest);

    void cancelOrder(long id);

    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
