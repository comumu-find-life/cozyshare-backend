package com.core.domain.deal.service;

import com.core.domain.user.dto.PaymentRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaypalService {

    @Value("${paypal.client-id}")
    private String clientId;

    @Value("${paypal.client-secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    private static final String PAYPAL_API_URL = "https://api-m.paypal.com/v1/payments/payment/";

    public boolean verifyPayment(final PaymentRequest request) throws JsonProcessingException {
        String accessToken = getAccessToken();
        String url = PAYPAL_API_URL + request.getPaymentId() + "/execute";
        Map<String, String> paymentDetails = new HashMap<>();
        paymentDetails.put("payer_id", request.getPayerId());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(paymentDetails, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return response.getStatusCode().is2xxSuccessful() && response.getBody().contains("approved");
    }

    private String getAccessToken() throws JsonProcessingException {
        String url = "https://api-m.paypal.com/v1/oauth2/token";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodeBase64(clientId + ":" + clientSecret));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> responseBody = mapper.readValue(response.getBody(), Map.class);
        return (String) responseBody.get("access_token");
    }


    private String encodeBase64(String value) {
        return java.util.Base64.getEncoder().encodeToString(value.getBytes());
    }
}