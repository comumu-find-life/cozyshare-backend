package com.core.domain.home.repository;

import com.core.domain.home.dto.HomeInformationResponse;
import com.core.domain.home.dto.HomeOverviewResponse;
import com.core.domain.home.model.Home;

import java.util.List;
import java.util.Optional;

public interface CustomHomeRepository {

    List<HomeOverviewResponse> findAllSellHome();

    List<Home> findByCity(final String cityName);

    List<Home> findByUserId(final Long userId);

    Optional<HomeInformationResponse> findHomeAndUserById(final Long homeId);

    List<HomeOverviewResponse> findByHomeIds(final List<Long> homeIds);
}