package com.example.paymentservice.config;

import com.example.paymentservice.repository.TransactionDetailsRepository;
import com.example.paymentservice.response.OrderEvent;
import com.example.paymentservice.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventListener {

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Autowired
    private IPaymentService paymentService;

    @KafkaListener(topics = "order-topic", groupId = "payment-group")
    public void handleOrderEvent(OrderEvent orderEvent){
        if("OrderCreated".equals(orderEvent.getStatus())){

        }
    }
}
