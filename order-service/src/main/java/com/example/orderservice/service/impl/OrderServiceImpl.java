package com.example.orderservice.service.impl;

import com.example.orderservice.config.PaymentMode;
import com.example.orderservice.entity.Order;
import com.example.orderservice.exception.OrderServiceCustomException;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.request.OrderRequest;
import com.example.orderservice.response.OrderEvent;
import com.example.orderservice.response.OrderResponse;
import com.example.orderservice.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderServiceImpl implements IOrderService {

    @Value(value = "${message.topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public long createOrder(OrderRequest orderRequest) {

        Order order = null;
        PaymentMode paymentMode = null;
        try{
            order = Order.builder()
                    .productId(orderRequest.getProductId())
                    .quantity(orderRequest.getQuantity())
                    .orderDate(new Date().toInstant())
                    .orderStatus("PAYMENT-PROCESS")
                    .amount(orderRequest.getTotalAmount())
                    .build();

            orderRepository.save(order);

        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        if("1".equals(orderRequest.getPaymentMode())){
            paymentMode = PaymentMode.PAYMENT_ON_DELIVERY;
        }else if("2".equals(orderRequest.getPaymentMode())){
            paymentMode = PaymentMode.PAYMENT_VIA_CARD;
        }else {
            paymentMode = PaymentMode.PAYMENT_VIA_VNPAY_WALLET;
        }

        OrderEvent orderEvent = new OrderEvent(order.getId(), paymentMode,"OrderCreated");
        kafkaTemplate.send(topicName, orderEvent);

        assert order != null;

        return order.getId();
    }

    @Override
    public void cancelOrder(long id) {
        try{
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new OrderServiceCustomException("Order not exits", "404", HttpStatus.NOT_FOUND.value()));
            order.setOrderStatus("ORDER-CANCEL");

            orderRepository.save(order);
        }catch (Exception e){
            System.out.println("Error cancle: " + e.getMessage());
        }
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
