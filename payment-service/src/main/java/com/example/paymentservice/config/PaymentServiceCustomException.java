package com.example.paymentservice.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentServiceCustomException {

    private final String errorCode;

    public PaymentServiceCustomException(String message, String errorCode){
        super();
        this.errorCode = errorCode;
    }
}
