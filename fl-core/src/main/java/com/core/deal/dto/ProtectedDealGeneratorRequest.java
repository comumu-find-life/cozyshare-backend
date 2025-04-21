package com.core.deal.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProtectedDealGeneratorRequest {

    private Long getterId;

    private Long providerId;

    private Long homeId;

    private Long dmId;

    private double deposit;

    private LocalDateTime dealAt;
}
