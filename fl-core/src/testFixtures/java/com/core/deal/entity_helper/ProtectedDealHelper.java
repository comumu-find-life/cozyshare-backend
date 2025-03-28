package com.core.deal.entity_helper;

import com.core.domain.deal.model.DealState;
import com.core.domain.deal.model.ProtectedDeal;
import com.core.domain.deal.model.ProtectedDealDateTime;

import java.time.LocalDateTime;

public class ProtectedDealHelper {

    public static ProtectedDeal generateProtectedDeal(Long homeId){
        return ProtectedDeal.builder()
                .homeId(homeId)
                .dealState(DealState.REQUEST_DEAL)
                .dmId(1L)
                .getterId(1L)
                .providerId(2L)
                .deposit(2000)
                .protectedDealDateTime(generateProtectedDealDateTime())
                .build();
    }

    public static ProtectedDeal generateProtectedDealWithGetterId(Long homeId, Long getterId){
        return ProtectedDeal.builder()
                .homeId(homeId)
                .dealState(DealState.REQUEST_DEAL)
                .dmId(1L)
                .getterId(getterId)
                .providerId(2L)
                .deposit(2000)
                .protectedDealDateTime(generateProtectedDealDateTime())
                .build();
    }

    public static ProtectedDeal generateProtectedDealWithUserIds(Long homeId, Long getterId, Long providerId){
        return ProtectedDeal.builder()
                .homeId(homeId)
                .dealState(DealState.REQUEST_DEAL)
                .dmId(1L)
                .getterId(getterId)
                .providerId(providerId)
                .deposit(2000)
                .protectedDealDateTime(generateProtectedDealDateTime())
                .build();
    }

    public static ProtectedDeal generateProtectedDealByDealState(Long homeId, DealState dealState){
        return ProtectedDeal.builder()
                .homeId(homeId)
                .dmId(1L)
                .getterId(2L)
                .providerId(1L)
                .deposit(2000)
                .protectedDealDateTime(generateProtectedDealDateTime())
                .dealState(dealState)
                .build();
    }

    public static ProtectedDealDateTime generateProtectedDealDateTime(){
        return ProtectedDealDateTime.builder()
                .createAt(LocalDateTime.of(2024,10,31,14,15))
                //.dealStartDateTime(LocalDateTime.of(2024,10,31,14,15))
                .build();
    }


}
