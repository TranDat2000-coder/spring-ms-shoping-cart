package com.example.paymentservice.service.impl;

import com.example.paymentservice.entity.TransactionDetails;
import com.example.paymentservice.exception.PaymentServiceCustomException;
import com.example.paymentservice.repository.TransactionDetailsRepository;
import com.example.paymentservice.request.PaymentRequest;
import com.example.paymentservice.response.PaymentResponse;
import com.example.paymentservice.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public long doPayment(PaymentRequest paymentRequest) {

        TransactionDetails transactionDetails = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();

        transactionDetails = transactionDetailsRepository.save(transactionDetails);

        return transactionDetails.getId();
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
