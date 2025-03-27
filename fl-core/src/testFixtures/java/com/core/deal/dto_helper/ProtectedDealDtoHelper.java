package com.core.deal.dto_helper;

import com.core.domain.deal.dto.ProtectedDealFindRequest;
import com.core.domain.deal.dto.ProtectedDealGeneratorRequest;

import java.time.LocalDateTime;

public class ProtectedDealDtoHelper {

    public static ProtectedDealFindRequest generateProtectedDealFindRequest(Long homeId){
        return ProtectedDealFindRequest.builder()
                .homeId(homeId)
                .dmId(1L)
                .getterId(1L)
                .providerId(2L)
                .build();
    }

    public static ProtectedDealGeneratorRequest generateProtectedDealGeneratorRequest(Long homeId) {
        return ProtectedDealGeneratorRequest.builder()
                .getterId(1L)
                .providerId(2L)
                .homeId(homeId)
                .dmId(1L)
                .deposit(1000)
                .dealAt(LocalDateTime.now())
                .build();
    }
}
