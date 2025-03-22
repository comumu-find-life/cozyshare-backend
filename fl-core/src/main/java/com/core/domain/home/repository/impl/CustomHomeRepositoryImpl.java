package com.core.domain.home.repository.impl;


import com.core.domain.home.dto.HomeInformationResponse;
import com.core.domain.home.dto.HomeOverviewResponse;
import com.core.domain.home.model.Home;
import com.core.domain.home.model.HomeStatus;
import com.core.domain.home.model.QHome;
import com.core.domain.home.model.QHomeImage;
import com.core.domain.user.model.QUser;
import com.core.domain.user.model.User;
import com.core.mapper.HomeMapper;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CustomHomeRepositoryImpl implements com.core.domain.home.repository.CustomHomeRepository {

    private final JPAQueryFactory query;
    private final QHome qHome = QHome.home;
    private final QHomeImage qHomeImage = QHomeImage.homeImage;
    private final QUser qUser = QUser.user;


    @Override
    public Optional<HomeInformationResponse> findHomeAndUserById(Long homeId) {
        Tuple tuple = query
                .select(qHome, qUser)
                .distinct()
                .from(qHome)
                .join(qUser).on(qHome.userIdx.eq(qUser.id))
                .join(qHome.images, qHomeImage).fetchJoin()
                .distinct()
                .where(qHome.id.eq(homeId))
                .fetchFirst();
        if (tuple == null) {
            return Optional.empty();
        }
        Home home = tuple.get(0, Home.class);
        User user = tuple.get(1, User.class);
        return Optional.ofNullable(HomeMapper.INSTANCE.toHomeInformation(home, user));
    }

    @Override
    public List<Home> findByUserId(Long userIdx) {
        List<Home> homes = query.selectFrom(qHome)
                .where(qHome.userIdx.eq(userIdx))
                .fetch();
        return homes;
    }

    @Override
    public List<HomeOverviewResponse> findFavoriteHomes(List<Long> homeIds) {
        List<Tuple> tuples = query
                .select(qHome, qUser)
                .from(qHome)
                .leftJoin(qUser).on(qHome.userIdx.eq(qUser.id))
                .where(qHome.id.in(homeIds)) // Home ID 필터링
                .fetch();

        return tuples.stream()
                .map(tuple -> {
                    Home home = tuple.get(0, Home.class);
                    User user = tuple.get(1, User.class);
                    return HomeMapper.INSTANCE.toSimpleHomeDto(home, user);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<HomeOverviewResponse> findAllSellHome() {
        Set<Long> seenHomeIds = new HashSet<>();
        List<Tuple> tuples = query.select(qHome, qUser)
                .from(qHome)
                .join(qUser).on(qHome.userIdx.eq(qUser.id))
                .leftJoin(qHome.images, qHomeImage).fetchJoin()
                .where(qHome.homeStatus.eq(HomeStatus.FOR_SALE))
                .fetch()
                .stream()
                .filter(tuple -> {
                    Home home = tuple.get(QHome.home);
                    return seenHomeIds.add(home.getId());
                })
                .toList();

        return tuples.stream()
                .map(tuple -> {
                    Home home = tuple.get(QHome.home);
                    User user = tuple.get(QUser.user);
                    return HomeMapper.INSTANCE.toSimpleHomeDto(home, user);
                })
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