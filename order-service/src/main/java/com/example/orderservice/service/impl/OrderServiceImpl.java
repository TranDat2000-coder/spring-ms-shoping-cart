package com.example.orderservice.service.impl;

import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.request.OrderRequest;
import com.example.orderservice.response.OrderEvent;
import com.example.orderservice.response.OrderResponse;
import com.example.orderservice.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void createOrder(OrderRequest orderRequest) {

        try{
            Order order = Order.builder()
                    .productId(orderRequest.getProductId())
                    .quantity(orderRequest.getQuantity())
                    .orderDate(new Date().toInstant())
                    .orderStatus("SUCCESS")
                    .amount(orderRequest.getTotalAmount())
                    .build();

            orderRepository.save(order);

        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        OrderEvent orderEvent = new OrderEvent(orderRequest.getProductId(), "OrderCreated");
        kafkaTemplate.send("order-topic", orderEvent);
    }

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        return 0;
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        return null;
    }
}
