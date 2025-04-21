package com.core.deal.repository.impl;

import com.core.deal.dto.ProtectedDealResponse;
import com.core.deal.model.ProtectedDeal;
import com.core.deal.model.QProtectedDeal;
import com.core.deal.repository.CustomProtectedDealRepository;
import com.core.home.model.Home;
import com.core.home.model.QHome;
import com.core.mapper.ProtectedDealMapper;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomProtectedDealRepositoryImpl implements CustomProtectedDealRepository {

    private final JPAQueryFactory query;
    private final QProtectedDeal qProtectedDeal = QProtectedDeal.protectedDeal;
    private final QHome qHome = QHome.home;

    @Override
    public List<ProtectedDeal> findAllByUserId(Long userId) {
        return query.selectFrom(qProtectedDeal)
                .where(qProtectedDeal.getterId.eq(userId)
                        .or(qProtectedDeal.providerId.eq(userId)))
                .fetch();
    }

    @Override
    public List<ProtectedDealResponse> findProtectedDealsByFilters(Long getterId, Long providerId, Long homeId, Long dmId) {
        List<ProtectedDealResponse> responses = new ArrayList<>();
        List<Tuple> fetch = query.select(qProtectedDeal, qHome)
                .from(qProtectedDeal)
                .join(qHome).on(qProtectedDeal.homeId.eq(qHome.id))
                .leftJoin(qProtectedDeal.protectedDealDateTime).fetchJoin()
                .where(qProtectedDeal.getterId.eq(getterId),
                        qProtectedDeal.providerId.eq(providerId),
                        qProtectedDeal.homeId.eq(homeId),
                        qProtectedDeal.dmId.eq(dmId))
                .fetch();
        fetch.stream()
                .forEach(tuple -> {
                    ProtectedDeal protectedDeal= tuple.get(QProtectedDeal.protectedDeal);
                    Home home = tuple.get(QHome.home);
                    responses.add(ProtectedDealMapper.INSTANCE.toResponse(protectedDeal, home));
                });
        return responses;
    }
}
