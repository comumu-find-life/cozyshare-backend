package com.infra.payment;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface PaymentService {

    boolean validatePayment(String paymentId, String payerId, String token, double amount) throws JsonProcessingException;
}
