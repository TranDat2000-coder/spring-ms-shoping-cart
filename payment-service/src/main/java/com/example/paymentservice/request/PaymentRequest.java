package com.example.paymentservice.request;

import com.example.paymentservice.config.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    private Long orderId;

    private Long amount;

    private String referenceNumber;

    private PaymentMode paymentMode;
}
