package com.example.paymentservice.response;

import com.example.paymentservice.common.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private long paymentId;

    private String status;

    private PaymentMode paymentMode;

    private long amount;

    private Instant paymentDate;

    private long orderId;
}
