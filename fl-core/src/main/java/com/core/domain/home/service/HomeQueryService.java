package com.core.domain.home.service;

import com.core.mapper.HomeMapper;
import com.core.domain.home.dto.HomeInformationResponse;
import com.core.domain.home.dto.HomeOverviewResponse;
import com.core.domain.home.dto.HomeOverviewWrapper;
import com.core.domain.home.model.Home;
import com.core.domain.home.repository.HomeRepository;
import com.core.domain.user.model.User;
import com.core.domain.user.repository.UserRepository;
import com.infra.utils.OptionalUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.infra.exception.ExceptionMessages.NOT_EXIST_HOME_ID;
import static com.infra.exception.ExceptionMessages.NOT_EXIST_USER_ID;

@Service
@RequiredArgsConstructor
public class HomeQueryService {

    private final HomeRepository homeRepository;
    private final UserRepository userRepository;
    private final HomeMapper homeMapper;

    public HomeInformationResponse findById(final Long id) {
        return OptionalUtil.getOrElseThrow(homeRepository.findHomeAndUserById(id),NOT_EXIST_HOME_ID);
    }

    /**
     * 모든 집 게시글 조회
     */
    @Cacheable(value = "homeOverviewCache", key = "'allHomes'")
    public HomeOverviewWrapper findAllHomes() {
        List<HomeOverviewResponse> homeOverviewResponses = homeRepository.findAllSellHome();
        return new HomeOverviewWrapper(homeOverviewResponses);
    }

    /**
     * 특정 사용자의 집 게시물 모두 조회
     */
    public List<HomeOverviewResponse> findByUserId(final Long userId) {
        List<HomeOverviewResponse> response = new ArrayList<>();
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(userId), NOT_EXIST_USER_ID);
        List<Home> homes = homeRepository.findByUserId(userId);
        homes.forEach(home -> {
            response.add(homeMapper.toSimpleHomeDto(home, user));
        });
        return response;
    }

    /**
     * 찜 목록 게시글 조회
     */
    public List<HomeOverviewResponse> findFavoriteHomes(final List<Long> homeIds) {
        return homeRepository.findByHomeIds(homeIds);
    }


    /**
     * 도시 이름으로 판매중인 집 게시글 조회
     */
    public List<HomeOverviewResponse> findByCity(final String cityName) {
        List<Home> homes = homeRepository.findByCity(cityName);
        return homes.stream()
                .map(home -> {
                    User user = userRepository.findById(home.getUserId())
                            .orElseThrow(() -> new EntityNotFoundException(NOT_EXIST_USER_ID + home.getUserId()));
                    return homeMapper.toSimpleHomeDto(home, user);
                })
                .collect(Collectors.toList());
    }
}
