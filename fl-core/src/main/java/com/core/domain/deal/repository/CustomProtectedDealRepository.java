package com.core.domain.deal.repository;

import com.core.domain.deal.dto.ProtectedDealResponse;
import com.core.domain.deal.model.ProtectedDeal;

import java.util.List;

public interface CustomProtectedDealRepository {

    List<ProtectedDeal> findAllByUserId(Long userId);

    List<ProtectedDealResponse> findProtectedDealsByFilters(Long getterId, Long providerId, Long homeId, Long dmId);

}
