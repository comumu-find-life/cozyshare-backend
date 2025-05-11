package com.api.v1.home;

import com.core.user.dto.UserInformationResponse;
import com.core.user.service.UserService;
import com.core.home.dto.*;
import com.infra.utils.SuccessResponse;
import com.core.home.service.HomeQueryService;
import com.core.home.service.HomeService;
import com.core.home.service.LocationService;
import com.core.home.model.LatLng;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.api.auth.service.SecurityContextHelper.getLoginEmailBySecurityContext;
import static com.api.v1.constants.ApiUrlConstants.*;
import static com.api.v1.constants.ResponseMessage.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeQueryService homeQueryService;
    private final HomeService homeService;
    private final LocationService locationService;
    private final UserService userService;

    /**
     * 집 게시물 저장 API
     */
    @PostMapping(HOMES_BASE_URL)
    public ResponseEntity<?> saveHome(@RequestPart final HomeGeneratorRequest homeGeneratorRequest,
                                      @RequestPart("images") final List<MultipartFile> images) throws IllegalAccessException {
        LatLng location = locationService.getLatLngFromAddress(homeGeneratorRequest.getHomeAddress());
        Long homeId = homeService.save(getLoggedInUserId(), homeGeneratorRequest, images, location);
        SuccessResponse response = new SuccessResponse(true, HOME_POST_SUCCESS, homeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 올바른 주소를 검증하는 API
     */
    @PostMapping(HOMES_VALIDATE_ADDRESS)
    public ResponseEntity<?> validateAddress(@RequestBody final HomeAddressGeneratorRequest homeAddressGeneratorRequest) throws IllegalAccessException {
        LatLng location = locationService.getLatLngFromAddress(homeAddressGeneratorRequest);
        SuccessResponse response = new SuccessResponse(true, ADDRESS_VALIDATION_SUCCESS, location);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 자신의 모든 집 게시글 조회 API
     */
    @GetMapping(HOMES_FIND_BY_USER_ID)
    public ResponseEntity<?> findByUserId(@PathVariable final Long userId) {
        List<HomeOverviewResponse> homes = homeQueryService.findByUserId(userId);
        SuccessResponse response = new SuccessResponse(true, USER_POSTS_RETRIEVE_SUCCESS, homes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * PK 를 이용한 집 게시글 단일 조회 API
     */
    @GetMapping(HOMES_FIND_BY_ID)
    public ResponseEntity<?> findById(@PathVariable final Long homeId) {
        HomeInformationResponse homeInformationResponse = homeQueryService.findById(homeId);
        SuccessResponse response = new SuccessResponse(true, HOME_RETRIEVE_SUCCESS, homeInformationResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 판매중인 모든 집 게시글 조회 API
     */
    @GetMapping(HOMES_FIND_ALL)
    public ResponseEntity<?> findAllHomesForSale() {
        HomeOverviewWrapper allHomes = homeQueryService.findAllHomesForSale();
        SuccessResponse response = new SuccessResponse(true, ALL_HOMES_RETRIEVE_SUCCESS, allHomes.getHomes());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 판매중인 집 게시글 페이징 조회 API
     */
    @GetMapping(HOMES_BASE_URL)
    public ResponseEntity<?> findHomesForSaleByPaging(Pageable pageable) {
        HomeOverviewWrapper homes = homeQueryService.findHomesForSaleByPaging(pageable);
        SuccessResponse response = new SuccessResponse(true, HOMES_PAGING_SUCCESS, homes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * city 이름으로 집 게시글 조회 API
     */
    @GetMapping(HOMES_FIND_BY_CITY)
    public ResponseEntity<?> findByCity(@RequestParam final String city) {
        List<HomeOverviewResponse> homes = homeQueryService.findByCity(city);
        SuccessResponse<Object> response = new SuccessResponse<>(true, CITY_HOMES_RETRIEVE_SUCCESS, homes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 찜 목록 집 게시글 조회 API
     */
    @GetMapping(HOMES_FIND_FAVORITE)
    public ResponseEntity<?> findFavoriteHomes(@RequestParam final List<Long> homeIds) {
        List<HomeOverviewResponse> favoriteHomes = homeQueryService.findFavoriteHomes(homeIds);
        SuccessResponse response = new SuccessResponse<>(true, FAVORITE_HOMES_RETRIEVE_SUCCESS, favoriteHomes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * 집 게시글 수정 API
     */
    @PatchMapping(HOMES_BASE_URL)
    public ResponseEntity<?> updateHome(@RequestBody final HomeUpdateRequest homeDto) {
        homeService.update(homeDto);
        SuccessResponse response = new SuccessResponse(true, HOME_UPDATE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 집 게시글 이미지 수정 API
     */
    @PatchMapping(HOMES_UPDATE_IMAGE)
    public ResponseEntity<?> updateHomeImage(@PathVariable final Long homeId, @RequestPart("images") final List<MultipartFile> images) {
        homeService.updateHomeImages(homeId, images);
        SuccessResponse response = new SuccessResponse(true, HOME_IMAGE_UPDATE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 집 게시글 상태 변경(판매완료 <-> 판매중) API
     */
    @PatchMapping(HOMES_CHANGE_STATUS)
    public ResponseEntity<?> changeStatusHome(@PathVariable final Long homeId, @PathVariable final String status) {
        homeService.changeStatus(homeId, status);
        SuccessResponse response = new SuccessResponse(true, HOME_SELL_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * 집 게시글 삭제 API
     */
    @DeleteMapping(HOMES_DELETE)
    public ResponseEntity<?> delete(@PathVariable final Long homeId) {
        homeService.delete(homeId);
        SuccessResponse response = new SuccessResponse<>(true, HOME_DELETE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 집 게시글 이미지 삭제 API
     */
    @DeleteMapping(HOMES_UPDATE_IMAGE)
    public ResponseEntity<?> deleteHomeImage(@PathVariable final Long homeId, @RequestParam final List<String> imageUrls) {
        homeService.deleteHomeImage(imageUrls);
        SuccessResponse<Object> response = new SuccessResponse<>(true, HOME_IMAGE_DELETE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    private Long getLoggedInUserId() {
        String email = getLoginEmailBySecurityContext();
        UserInformationResponse user = userService.findByEmail(email);
        return user.getId();
    }
}

