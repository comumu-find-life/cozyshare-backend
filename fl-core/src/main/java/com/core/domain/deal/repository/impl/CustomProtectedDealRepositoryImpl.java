package com.core.domain.deal.repository.impl;

import com.core.domain.deal.model.DealState;
import com.core.domain.deal.model.ProtectedDeal;
import com.core.domain.deal.model.QProtectedDeal;
import com.core.domain.deal.repository.CustomProtectedDealRepository;
import com.core.domain.home.model.QHome;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public List<Tuple> findByMultipleParams(Long getterId, Long providerId, Long homeId, Long dmId) {
        return query.select(qProtectedDeal, qHome)
                .from(qProtectedDeal)
                .join(qHome).on(qProtectedDeal.homeId.eq(qHome.id))
                .leftJoin(qProtectedDeal.protectedDealDateTime).fetchJoin()  // fetchJoin 추가
                .where(qProtectedDeal.getterId.eq(getterId),
                        qProtectedDeal.providerId.eq(providerId),
                        qProtectedDeal.homeId.eq(homeId),
                        qProtectedDeal.dmId.eq(dmId))
                .fetch();
    }

    @Override
    public List<ProtectedDeal> findAllRequestDeposit() {
        return query.selectFrom(qProtectedDeal)
                .where(qProtectedDeal.dealState.eq(DealState.REQUEST_DEAL))
                .fetch();
    }

    @Override
    public List<ProtectedDeal> findAllSubmitDeal() {
        return query.selectFrom(qProtectedDeal)
                .where(qProtectedDeal.dealState.eq(DealState.ACCEPT_DEAL))
                .fetch();
    }
}
