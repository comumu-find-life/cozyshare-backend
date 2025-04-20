package com.api.v1.point;

import com.core.domain.user.dto.PaymentRequest;
import com.infra.payment.PaymentService;
import com.infra.utils.SuccessResponse;
import com.infra.exception.custom.InsufficientPointsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.core.domain.deal.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.api.auth.service.SecurityContextHelper.getLoginEmailBySecurityContext;
import static com.api.v1.constants.ApiUrlConstants.*;
import static com.api.v1.constants.ResponseMessage.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PointController {

    private final PaymentService paymentService;
    private final PointService pointService;

    @PostMapping(CHARGE_POINT_BY_PAYPAL)
    public ResponseEntity<?> paymentSuccess(@RequestBody final  PaymentRequest request) throws JsonProcessingException {
        boolean isPayment = paymentService.validatePayment(request.getPaymentId(), request.getPayerId(), request.getToken(), request.getAmount());
        if(isPayment){
            String email = getLoginEmailBySecurityContext();
            pointService.chargePoint(email, request.getAmount());
            SuccessResponse response = new SuccessResponse(true, CHARGE_SUCCESS, null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        SuccessResponse response = new SuccessResponse(true, CHARGE_FAILED, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @GetMapping("/paypal/success")
    public ResponseEntity<String> paypalSuccess(
            @RequestParam String paymentId,
            @RequestParam String token,
            @RequestParam String PayerID
    ) {
        return ResponseEntity.ok("success");
    }

    @GetMapping("/paypal/cancel")
    public ResponseEntity<String> paypalCancel(
            @RequestParam(required = false) String token
    ) {
        return ResponseEntity.ok("cancelled");
    }

    @PostMapping(APPLY_WITH_DRAW_URL)
    public ResponseEntity<?> applyWithDraw(@RequestParam final double price) throws InsufficientPointsException {
        String email = getLoginEmailBySecurityContext();
        pointService.applyWithDraw(email, price);
        SuccessResponse response = new SuccessResponse(true, APPLY_WITH_DRAW, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
