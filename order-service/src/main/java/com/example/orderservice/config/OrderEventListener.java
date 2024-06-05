package com.example.orderservice.config;

import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.OrderServiceCustomException;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.response.PaymentEvent;
import com.example.orderservice.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventListener {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @KafkaListener(topics = "payment-topic", groupId = "order-group")
    public void handlePaymentEvent(PaymentEvent paymentEvent){

        if("PaymentProcessed".equals(paymentEvent.getStatus())){

            Order order = orderRepository.findById(paymentEvent.getOrderId())
                    .orElseThrow(() -> new OrderServiceCustomException("Order not exits", "404", HttpStatus.NOT_FOUND.value()));

            order.setOrderStatus("ORDER-CANCEL");

            orderRepository.save(order);
        }else {
            orderService.cancelOrder(paymentEvent.getOrderId());
        }
    }
}
