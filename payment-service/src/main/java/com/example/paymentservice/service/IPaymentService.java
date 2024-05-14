package com.example.paymentservice.service;

import com.example.paymentservice.request.PaymentRequest;
import com.example.paymentservice.response.PaymentResponse;

public interface IPaymentService {

    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(long id);
}
