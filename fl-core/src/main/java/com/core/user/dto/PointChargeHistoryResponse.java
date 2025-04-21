package com.core.user.dto;

import com.core.user.model.ChargeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointChargeHistoryResponse {

    private double chargeAmount;

    private LocalDateTime chargeDate;

    private ChargeType chargeType;
}
