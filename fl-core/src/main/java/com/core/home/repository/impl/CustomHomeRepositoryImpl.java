package com.core.home.repository.impl;


import com.core.home.dto.HomeInformationResponse;
import com.core.home.dto.HomeOverviewResponse;
import com.core.home.model.Home;
import com.core.home.model.HomeStatus;
import com.core.home.model.QHome;
import com.core.home.model.QHomeImage;
import com.core.user.model.QUser;
import com.core.user.model.User;
import com.core.home.repository.CustomHomeRepository;
import com.core.mapper.HomeMapper;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class CustomHomeRepositoryImpl implements CustomHomeRepository {

    private final JPAQueryFactory query;
    private final QHome qHome = QHome.home;
    private final QUser qUser = QUser.user;
    private final QHomeImage qHomeImage = QHomeImage.homeImage;


    @Override
    public Optional<HomeInformationResponse> findHomeInformationById(Long homeId) {
        Tuple tuple = query
                .select(qHome, qUser)
                .from(qHome)
                .join(qUser).on(qHome.userId.eq(qUser.id))
                .join(qHome.images, qHomeImage).fetchJoin()
                .distinct()
                .where(qHome.id.eq(homeId))
                .fetchFirst();
        if (tuple == null) {
            return Optional.empty();
        }
        Home home = tuple.get(qHome);
        User user = tuple.get(qUser);
        return Optional.ofNullable(HomeMapper.INSTANCE.toHomeInformation(home, user));
    }

    @Override
    public List<Home> findByUserId(Long userId) {
        List<Home> homes = query.selectFrom(qHome)
                .where(qHome.userId.eq(userId ))
                .fetch();
        return homes;
    }

    @Override
    public List<HomeOverviewResponse> findByHomeIds(List<Long> homeIds) {
        List<Tuple> tuples = query
                .select(qHome, qUser)
                .from(qHome)
                .leftJoin(qUser).on(qHome.userId.eq(qUser.id))
                .join(qHome.images, qHomeImage).fetchJoin()
                .where(qHome.id.in(homeIds))
                .fetch();

        Map<Long, HomeOverviewResponse> responseMap = new LinkedHashMap<>();

        for (Tuple tuple : tuples) {
            Home home = tuple.get(qHome);
            User user = tuple.get(qUser);
            if (!responseMap.containsKey(home.getId())) {
                HomeOverviewResponse response = HomeMapper.INSTANCE.toOverviewResponse(home, user);
                responseMap.put(home.getId(), response);
            }
        }

        return new ArrayList<>(responseMap.values());
    }

    @Override
    public List<HomeOverviewResponse> findAllSellHome() {
        Set<Long> seenIds = new HashSet<>();
        return query.select(qHome, qUser)
                .from(qHome)
                .join(qUser).on(qHome.userId.eq(qUser.id))
                .leftJoin(qHome.images, qHomeImage).fetchJoin()
                .where(qHome.homeStatus.eq(HomeStatus.FOR_SALE))
                .fetch()
                .stream()
                .filter(tuple -> seenIds.add(tuple.get(qHome).getId()))
                .map(tuple -> HomeMapper.INSTANCE.toOverviewResponse(
                        tuple.get(qHome), tuple.get(qUser)))
                .toList();
    }

    @Override
    public List<Home> findByCity(String cityName) {
        List<Home> homes = query.selectFrom(qHome)
                .where(qHome.homeStatus.eq(HomeStatus.FOR_SALE))
                .where(qHome.homeAddress.city.like("%" + cityName +"%"))
                .fetch();
        return homes;
    }
}