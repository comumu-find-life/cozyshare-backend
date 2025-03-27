package com.core.mapper;


import com.core.domain.home.dto.HomeAddressGeneratorRequest;
import com.core.domain.home.dto.HomeGeneratorRequest;
import com.core.domain.home.dto.HomeUpdateRequest;
import com.core.domain.home.dto.HomeInformationResponse;
import com.core.domain.home.dto.HomeOverviewResponse;
import com.core.domain.home.model.Home;
import com.core.domain.home.model.HomeAddress;
import com.core.domain.home.model.HomeImage;
import com.core.domain.home.model.HomeInfo;
import com.core.domain.user.model.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HomeMapper {

    HomeMapper INSTANCE = Mappers.getMapper(HomeMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "homeStatus", expression = "java(com.core.domain.home.model.HomeStatus.FOR_SALE)")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "homeInfo", source = "homeDto", qualifiedByName = "mapHomeInfo")
    Home toEntity(HomeGeneratorRequest homeDto, Long userId);

    @Named("mapHomeInfo")
    default HomeInfo mapHomeInfo(HomeGeneratorRequest homeGeneratorRequest){
        return HomeInfo.builder()
                .canParking(homeGeneratorRequest.isCanParking())
                .bathRoomCount(homeGeneratorRequest.getBathRoomCount())
                .bedroomCount(homeGeneratorRequest.getBedroomCount())
                .residentCount(homeGeneratorRequest.getResidentCount())
                .options(homeGeneratorRequest.getOptions())
                .bond(homeGeneratorRequest.getBond())
                .bill(homeGeneratorRequest.getBill())
                .rent(homeGeneratorRequest.getRent())
                .introduce(homeGeneratorRequest.getIntroduce())
                .gender(homeGeneratorRequest.getGender())
                .type(homeGeneratorRequest.getType())
                .build();
    }

    /**
     * HomeAddress 엔티티 변환
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    HomeAddress toAddressEntity(HomeAddressGeneratorRequest homeAddressDto);

    /**
     * 집 정보 수정
     */
    void updateHomeFromDto(HomeUpdateRequest dto, @MappingTarget HomeInfo entity);

    /**
     * 집 주소 수정
     */
    @Mapping(target = "id", ignore = true)
    void updateAddressFromDto(HomeAddressGeneratorRequest dto, @MappingTarget HomeAddress entity);

    /**
     * Home 게시글을 보여줄 DTO 변환
     */
    @Mapping(target = "providerId", source = "user.id")
    @Mapping(target = "homeId", source = "home.id")
    @Mapping(target = "latitude", source = "home.homeAddress.latitude")
    @Mapping(target = "longitude", source = "home.homeAddress.longitude")
    @Mapping(target = "introduce", source = "home.homeInfo.introduce")
    @Mapping(target = "providerProfileUrl", source = "user.profileUrl")
    @Mapping(target = "providerName", source = "user.nickname")
    @Mapping(target = "rent", source = "home.homeInfo.rent")
    @Mapping(target = "bond", source = "home.homeInfo.bond")
    @Mapping(target = "gender", source = "home.homeInfo.gender")
    @Mapping(target = "type", source = "home.homeInfo.type")
    @Mapping(target = "bill", source = "home.homeInfo.bill")
    @Mapping(target = "bedroomCount", source = "home.homeInfo.bedroomCount")
    @Mapping(target = "bathRoomCount", source = "home.homeInfo.bathRoomCount")
    @Mapping(target = "residentCount", source = "home.homeInfo.residentCount")
    @Mapping(target = "options", source = "home.homeInfo.options")
    @Mapping(target = "address", source = "home.homeAddress", qualifiedByName = "mapSimpleAddress")
    @Mapping(target = "images", source = "home.images", qualifiedByName = "mapImageUrls")
    @Mapping(target = "canParking", source = "home.homeInfo.canParking")
    HomeInformationResponse toHomeInformation(Home home, User user);

    /**
     * Home 을 리스트로 보여줄 DTO 변환
     */
    @Mapping(target = "id", source = "home.id")
    @Mapping(target = "address", source = "home.homeAddress", qualifiedByName = "mapSimpleAddress")
    @Mapping(target = "latitude", source = "home.homeAddress.latitude")
    @Mapping(target = "longitude", source = "home.homeAddress.longitude")
    @Mapping(target = "mainImage", source = "home.images", qualifiedByName = "mapMainImage")
    @Mapping(target = "rent", source = "home.homeInfo.rent")
    @Mapping(target = "bond", source = "home.homeInfo.bond")
    @Mapping(target = "bill", source = "home.homeInfo.bill")
    @Mapping(target = "bedroomCount", source = "home.homeInfo.bedroomCount")
    @Mapping(target = "bathRoomCount", source = "home.homeInfo.bathRoomCount")
    @Mapping(target = "type", source = "home.homeInfo.type")
    @Mapping(target = "homeStatus", source = "home.homeStatus")
    @Mapping(target = "userIdx", source = "user.id")
    @Mapping(target = "userName", source = "user.nickname")
    HomeOverviewResponse toSimpleHomeDto(Home home, User user);


    @Named("mapImageUrls")
    default List<String> mapImageUrls(List<HomeImage> images) {
        return images.stream()
                .map(HomeImage::getImageUrl)
                .collect(Collectors.toList());
    }

    @Named("mapMainImage")
    default String mapMainImage(List<HomeImage> images) {
        return images.get(0).getImageUrl();
    }

    @Named("mapSimpleAddress")
    default String mapSimpleAddress(HomeAddress homeAddress) {
        StringBuilder sb = new StringBuilder();
        sb.append(homeAddress.getStreetCode() + " ");
        sb.append(homeAddress.getStreetName() + ", ");
        sb.append(homeAddress.getCity() + " ");
        sb.append(homeAddress.getState() + " ");
        sb.append(homeAddress.getPostCode());
        return sb.toString();
    }


}
