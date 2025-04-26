package com.core.home.service;

import com.core.home.dto.HomeOverviewResponse;
import com.core.home.dto.HomeOverviewWrapper;
import com.core.home.model.Home;
import com.core.home.model.HomeDocument;
import com.core.home.model.HomeStatus;
import com.core.home.repository.HomeElasticRepository;
import com.core.home.repository.HomeRepository;
import com.core.mapper.HomeMapper;
import com.core.user.model.User;
import com.core.user.repository.UserRepository;
import com.infra.exception.ExceptionMessages;
import com.infra.utils.OptionalUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.infra.exception.ExceptionMessages.NOT_EXIST_HOME_ID;

@Service
@RequiredArgsConstructor
public class HomeElasticService {

    private final HomeElasticRepository homeElasticRepository;
    private final HomeMapper homeMapper;

    public void saveHomeToElastic(final Home home, final User user) {
        HomeDocument document = homeMapper.homeToHomeDocument(home, user);
        homeElasticRepository.save(document);
    }

    @Cacheable(value = "homeOverviewCache", key = "'allHomes'")
    public HomeOverviewWrapper findAllSellHomes() {
        List<HomeDocument> homeDocuments = homeElasticRepository.findByHomeStatusIn(List.of(HomeStatus.FOR_SALE, HomeStatus.DURING_SELL));
        return new HomeOverviewWrapper(homeDocuments.stream()
                .map(homeDocument -> homeMapper.homeDocumentToHomeOverviewResponse(homeDocument))
                .collect(Collectors.toList()));
    }

    public List<HomeOverviewResponse> findByCity(final String cityName){
        return homeElasticRepository.findByAddress(cityName).stream()
                .map(homeDocument -> homeMapper.homeDocumentToHomeOverviewResponse(homeDocument))
                .collect(Collectors.toList());
    }

    public void updateHomeStatus(final Long homeId, HomeStatus status) {
        HomeDocument homeDocument = findHomeDocument(homeId);
        homeDocument.setHomeStatus(status);
        homeElasticRepository.save(homeDocument);
    }

    public void deleteHomeFromElastic(Long homeId) {
        HomeDocument homeDocument = findHomeDocument(homeId);
        homeElasticRepository.delete(homeDocument);
    }

    public HomeDocument findHomeDocument(final Long homeId) {
        return homeElasticRepository.findById(homeId).orElseThrow(() -> new EntityNotFoundException(NOT_EXIST_HOME_ID));
    }
}
