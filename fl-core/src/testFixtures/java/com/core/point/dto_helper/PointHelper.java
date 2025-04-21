package com.core.point.dto_helper;

import com.core.user.dto.PaymentRequest;

public class PointHelper {

    public static PaymentRequest generatePaymentRequest(){
        return PaymentRequest.builder()
                .paymentId("paymentId")
                .payerId("payerId")
                .token("token")
                .amount(1000)
                .build();
    }
}
