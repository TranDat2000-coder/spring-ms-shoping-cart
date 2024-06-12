package com.example.paymentservice.config;

import com.example.paymentservice.entity.TransactionDetails;
import com.example.paymentservice.repository.TransactionDetailsRepository;
import com.example.paymentservice.response.OrderEvent;
import com.example.paymentservice.service.IPaymentService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventListener {

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Autowired
    private IPaymentService paymentService;

    @KafkaListener(topics = "order-topic", groupId = "payment-group", concurrency = "3", containerFactory = "orderKafkaListenerContainerFactory")
    public void listenGroupOrder(String message){
        Gson gson = new Gson();
        OrderEvent messageOrder = gson.fromJson(message, OrderEvent.class);

        try{
            if("OrderCreated".equals(messageOrder.getStatus())){
                TransactionDetails transactionDetails = TransactionDetails.builder()
                        .orderId(messageOrder.getOrderId())
                        .paymentMode(PaymentMode.valueOf(messageOrder.getPaymentMode().name()))
                        .build();

                transactionDetailsRepository.save(transactionDetails);
            }
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
