package com.example.paymentservice.response;

import com.example.paymentservice.config.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {

    private Long orderId;

    private PaymentMode paymentMode;

    private String status;
}
