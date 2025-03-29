package com.core.domain.deal.repository;

import com.core.domain.deal.model.ProtectedDeal;
import com.querydsl.core.Tuple;

import java.util.List;

public interface CustomProtectedDealRepository {

    List<ProtectedDeal> findAllByUserId(Long userId);

    List<Tuple> findByMultipleParams(Long getterId, Long providerId, Long homeId, Long dmId);
}
