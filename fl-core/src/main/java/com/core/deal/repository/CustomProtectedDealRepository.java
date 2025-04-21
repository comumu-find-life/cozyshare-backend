package com.core.deal.repository;

import com.core.deal.dto.ProtectedDealResponse;
import com.core.deal.model.ProtectedDeal;

import java.util.List;

public interface CustomProtectedDealRepository {

    List<ProtectedDeal> findAllByUserId(Long userId);

    List<ProtectedDealResponse> findProtectedDealsByFilters(Long getterId, Long providerId, Long homeId, Long dmId);

}
