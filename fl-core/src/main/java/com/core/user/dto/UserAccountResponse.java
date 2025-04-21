package com.core.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccountResponse {

    private String depositorName;

    private String paypalInformation;

    private double point;

    private List<PointChargeHistoryResponse> chargeHistories;
}
