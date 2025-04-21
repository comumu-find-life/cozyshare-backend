package com.core.user.dto;

import com.core.user.model.ChargeType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class WithDrawHistoryResponse {
    private Long userAccountId;
    private Long pointHistoryId;
    private String depositorName;
    private String paypalInformation;
    private double chargeAmount;
    private LocalDateTime historyDateTime;
    private ChargeType chargeType;
}
