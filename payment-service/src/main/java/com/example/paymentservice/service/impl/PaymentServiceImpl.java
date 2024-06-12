package com.example.paymentservice.service.impl;

import com.example.paymentservice.entity.TransactionDetails;
import com.example.paymentservice.exception.PaymentServiceCustomException;
import com.example.paymentservice.repository.TransactionDetailsRepository;
import com.example.paymentservice.request.PaymentRequest;
import com.example.paymentservice.response.PaymentEvent;
import com.example.paymentservice.response.PaymentResponse;
import com.example.paymentservice.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public void doPayment(PaymentRequest paymentRequest) {

        try{
            TransactionDetails transactionDetails = TransactionDetails.builder()
                    .orderId(paymentRequest.getOrderId())
                    .paymentMode(paymentRequest.getPaymentMode())
                    .referenceNumber(paymentRequest.getReferenceNumber())
                    .paymentDate(Instant.now())
                    .paymentStatus("PAYMENT-PROCESS")
                    .amount(paymentRequest.getAmount())
                    .build();

            transactionDetailsRepository.save(transactionDetails);

        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        PaymentEvent paymentEvent = new PaymentEvent(paymentRequest.getOrderId(), "PaymentProcessed");
        kafkaTemplate.send("payment-topic", paymentEvent);
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(long id) {

        TransactionDetails transactionDetails = transactionDetailsRepository.findByOrderId(id)
                .orElseThrow(() -> new PaymentServiceCustomException("TransactionDetails with given id not found", "TRANSACTION_NOT_FOUND"));

        PaymentResponse response = PaymentResponse.builder()
                .paymentId(transactionDetails.getId())
                .paymentMode(transactionDetails.getPaymentMode())
                .status(transactionDetails.getPaymentStatus())
                .paymentDate(transactionDetails.getPaymentDate())
                .orderId(transactionDetails.getOrderId())
                .amount(transactionDetails.getAmount())
                .build();

        return response;
    }
}
