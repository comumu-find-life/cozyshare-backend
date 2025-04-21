package com.core.home.repository;

import com.core.home.dto.HomeInformationResponse;
import com.core.home.dto.HomeOverviewResponse;
import com.core.home.model.Home;

import java.util.List;
import java.util.Optional;

public interface CustomHomeRepository {

    List<HomeOverviewResponse> findAllSellHome();

    List<Home> findByCity(final String cityName);

    List<Home> findByUserId(final Long userId);

    Optional<HomeInformationResponse> findHomeInformationById(final Long homeId);

    List<HomeOverviewResponse> findByHomeIds(final List<Long> homeIds);
}