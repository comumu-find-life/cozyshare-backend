package com.core.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentRequest {
    private final String paymentId;

    private final String payerId;

    private final String token;

    private final double amount;
}
