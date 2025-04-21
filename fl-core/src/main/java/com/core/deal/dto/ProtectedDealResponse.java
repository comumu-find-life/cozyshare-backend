package com.core.deal.dto;


import com.core.deal.model.DealState;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Getter 가 조회할 안전거래 정보
 */
@Getter
@Builder
public class ProtectedDealResponse {
    private Long id;
    private DealState dealState;
    private double deposit;
    private double fee;
    private double totalPrice;
    private LocalDateTime createAt;
    private LocalDateTime startAt;
    private LocalDateTime cancelAt;
    private LocalDateTime completeAt;
    private LocalDateTime dealAt;
    private String address;
    private String homeImage;
    private double rent;
    private double bill;
    private double bond;
}
