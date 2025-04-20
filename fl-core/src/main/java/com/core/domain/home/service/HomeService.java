package com.core.domain.home.service;

import com.core.domain.home.model.*;
import com.core.mapper.HomeMapper;
import com.core.domain.home.dto.HomeGeneratorRequest;
import com.core.domain.home.dto.HomeUpdateRequest;
import com.infra.exception.ExceptionMessages;
import com.infra.file.FileService;
import com.core.domain.home.repository.HomeImageRepository;
import com.core.domain.home.repository.HomeRepository;
import com.infra.utils.OptionalUtil;
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

import static com.infra.exception.ExceptionMessages.NOT_EXIST_HOME_ID;


@Slf4j
@Service
@RequiredArgsConstructor
public class HomeService {

    private final FileService fileService;
    private final HomeRepository homeRepository;
    private final HomeMapper homeMapper;
    private final HomeImageRepository homeImageRepository;

    @CacheEvict(value = "homeOverviewCache", key = "'allHomes'", allEntries = true)
    public Long save(final Long userId, final HomeGeneratorRequest homeCreateDto, final List<MultipartFile> files, final LatLng latLng) {
        Home home = homeMapper.toEntity(homeCreateDto, userId);
        if (hasFiles(files)) {
            home.setImages(uploadHomeImages(home, files));
        }
        home.setLatLng(latLng.getLat(), latLng.getLng());
        return homeRepository.save(home).getId();
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
