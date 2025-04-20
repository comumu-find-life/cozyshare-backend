package com.infra.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaypalPaymentService implements PaymentService{

    @Value("${paypal.client-id}")
    private String clientId;

    @Value("${paypal.client-secret}")
    private String clientSecret;

    @Value("${paypal.paypal-api-url}")
    private String paypalApiUrl;

    @Value("${paypal.paypal-access-token-url}")
    private String paypalAccessTokenURL;


    @Override
    public boolean validatePayment(String paymentId, String payerId, String token, double amount) throws JsonProcessingException {
        String accessToken = getAccessToken();
        String url = paypalApiUrl + paymentId + "/execute";
        Map<String, String> paymentDetails = new HashMap<>();
        paymentDetails.put("payer_id", payerId);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(paymentDetails, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return response.getStatusCode().is2xxSuccessful() && response.getBody().contains("approved");
    }

    private String getAccessToken() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodeBase64(clientId + ":" + clientSecret));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(paypalAccessTokenURL, HttpMethod.POST, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> responseBody = mapper.readValue(response.getBody(), Map.class);
        return (String) responseBody.get("access_token");
    }


    private String encodeBase64(String value) {
        return java.util.Base64.getEncoder().encodeToString(value.getBytes());
    }
}
