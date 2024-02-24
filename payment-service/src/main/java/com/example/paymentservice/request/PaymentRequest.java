package com.example.paymentservice.request;

import com.example.paymentservice.common.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    private long orderId;

    private long amount;

    private String refernceNumber;

    private PaymentMode paymentMode;
}
