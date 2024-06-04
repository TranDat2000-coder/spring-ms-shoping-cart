package com.example.paymentservice.request;

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

    private String paymentMode;
}
