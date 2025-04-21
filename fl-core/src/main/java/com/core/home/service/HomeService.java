package com.core.home.service;

import com.core.home.model.*;
import com.core.home.repository.HomeElasticRepository;
import com.core.mapper.HomeMapper;
import com.core.home.dto.HomeGeneratorRequest;
import com.core.home.dto.HomeUpdateRequest;
import com.core.user.model.User;
import com.core.user.repository.UserRepository;
import com.infra.exception.ExceptionMessages;
import com.infra.file.FileService;
import com.core.home.repository.HomeImageRepository;
import com.core.home.repository.HomeRepository;
import com.infra.utils.OptionalUtil;
import com.nimbusds.jose.crypto.opts.OptionUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.infra.exception.ExceptionMessages.NOT_EXIST_USER_ID;


@Slf4j
@Service
@RequiredArgsConstructor
public class HomeService {

    private final FileService fileService;
    private final HomeRepository homeRepository;
    private final UserRepository userRepository;
    private final HomeElasticRepository homeElasticRepository;
    private final HomeMapper homeMapper;
    private final HomeImageRepository homeImageRepository;

    @CacheEvict(value = "homeOverviewCache", key = "'allHomes'", allEntries = true)
    public Long save(final Long userId, final HomeGeneratorRequest request, final List<MultipartFile> files, final LatLng latLng) {
        User user = getUser(userId);
        Home home = createHomeEntity(userId, request, files, latLng);
        Home savedHome = saveHome(home);
        indexHomeInElasticsearch(savedHome, user);
        return savedHome.getId();
    }

    @Transactional
    public Long update(final HomeUpdateRequest homeUpdateDto) {
        Home home = findHomeById(homeUpdateDto.getHomeId());
        homeMapper.updateHomeFromDto(homeUpdateDto, home.getHomeInfo());
        homeMapper.updateAddressFromDto(homeUpdateDto.getHomeAddress(), home.getHomeAddress());
        return home.getId();
    }


    @Transactional
    public void updateHomeImages(final Long homeId, final List<MultipartFile> files) {
        Home home = findHomeById(homeId);
        if (hasFiles(files)) {
            home.addImages(uploadHomeImages(home, files));
        }
    }

    @Transactional
    public void deleteHomeImage(final List<String> imageUrls) {
        imageUrls.stream()
                .map(imageUrl -> homeImageRepository.findByImageUrl(imageUrl))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(homeImage -> {
                    homeImageRepository.delete(homeImage);
                    fileService.deleteFile(homeImage.getImageUrl());
                });
    }

    @CacheEvict(value = "homeOverviewCache", key = "'allHomes'", allEntries = true)
    public void delete(final Long homeId) {
        Home home = findHomeById(homeId);
        homeRepository.delete(home);
    }


    @Transactional
    @CacheEvict(value = "homeOverviewCache", key = "'allHomes'", allEntries = true)
    public void changeStatus(final Long homeId, final String status) {
        HomeStatus homeStatus = HomeStatus.fromString(status);
        Home home = findHomeById(homeId);
        home.setStatus(homeStatus);
    }

    private Home findHomeById(final Long homeId) {
        return homeRepository.findById(homeId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessages.NOT_EXIST_HOME_ID));
    }

    private boolean hasFiles(final List<MultipartFile> files) {
        return !files.isEmpty() && !files.get(0).getOriginalFilename().isEmpty();
    }

    private User getUser(Long userId) {
        return OptionalUtil.getOrElseThrow(userRepository.findById(userId), NOT_EXIST_USER_ID);
    }

    private Home createHomeEntity(Long userId, HomeGeneratorRequest request, List<MultipartFile> files, LatLng latLng) {
        Home home = homeMapper.toEntity(request, userId);
        if (hasFiles(files)) {
            home.setImages(uploadHomeImages(home, files));
        }
        home.setLatLng(latLng.getLat(), latLng.getLng());
        return home;
    }

    private Home saveHome(Home home) {
        return homeRepository.save(home);
    }

    private void indexHomeInElasticsearch(Home home, User user) {
        HomeDocument document = homeMapper.homeToHomeDocument(home, user);
        homeElasticRepository.save(document);
    }

    private List<HomeImage> uploadHomeImages(final Home home, final List<MultipartFile> files) {
        List<HomeImage> response = new ArrayList<>();
        for (MultipartFile file : files) {
            String url = fileService.toUrls(file);
            response.add(homeMapper.toHomeImage(home, url));
            fileService.fileUpload(file, url);
        }
        return response;
    }
}
