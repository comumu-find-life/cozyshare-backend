package com.core.deal;

import com.core.domain.deal.model.DealState;
import com.core.domain.deal.model.ProtectedDeal;
import com.core.domain.deal.model.ProtectedDealDateTime;

import java.time.LocalDateTime;

public class ProtectedDealHelper {

    public static ProtectedDeal createProtectedDeal(Long homeId){
        return ProtectedDeal.builder()
                .homeId(homeId)
                .dmId(1L)
                .getterId(2L)
                .providerId(1L)
                .deposit(2000)
                .protectedDealDateTime(createProtectedDealDateTime())
                .build();
    }

    public static ProtectedDeal createProtectedDealByDealState(Long homeId, DealState dealState){
        return ProtectedDeal.builder()
                .homeId(homeId)
                .dmId(1L)
                .getterId(2L)
                .providerId(1L)
                .deposit(2000)
                .protectedDealDateTime(createProtectedDealDateTime())
                .dealState(dealState)
                .build();
    }

    public static ProtectedDealDateTime createProtectedDealDateTime(){
        return ProtectedDealDateTime.builder()
                .createAt(LocalDateTime.of(2024,10,31,14,15))
                //.dealStartDateTime(LocalDateTime.of(2024,10,31,14,15))
                .build();
    }


}
