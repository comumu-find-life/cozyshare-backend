package com.core.mapper;


import com.core.home.dto.HomeAddressGeneratorRequest;
import com.core.home.dto.HomeGeneratorRequest;
import com.core.home.dto.HomeUpdateRequest;
import com.core.home.dto.HomeInformationResponse;
import com.core.home.dto.HomeOverviewResponse;
import com.core.home.model.*;
import com.core.user.model.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HomeMapper {

    HomeMapper INSTANCE = Mappers.getMapper(HomeMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "homeStatus", expression = "java(com.core.home.model.HomeStatus.FOR_SALE)")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "homeInfo", source = "homeDto", qualifiedByName = "mapHomeInfo")
    Home toEntity(HomeGeneratorRequest homeDto, Long userId);

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

    @Mapping(target = "home", source = "home")
    @Mapping(target = "imageUrl", source = "url")
    HomeImage toHomeImage(Home home, String url);


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
    HomeOverviewResponse toOverviewResponse(Home home, User user);

    @Mapping(target = "id", source = "home.id")
    @Mapping(target = "address", source = "home.homeAddress", qualifiedByName = "mapSimpleAddress")
    @Mapping(target = "latitude", source = "home.homeAddress.latitude")
    @Mapping(target = "longitude", source = "home.homeAddress.longitude")
    @Mapping(target = "mainImage", source = "home.images", qualifiedByName = "mapMainImage")
    @Mapping(target = "rent", source = "home.homeInfo.rent")
    @Mapping(target = "bond", source = "home.homeInfo.bond")
    @Mapping(target = "bill", source = "home.homeInfo.bill")
    @Mapping(target = "bedRoomCount", source = "home.homeInfo.bedroomCount")
    @Mapping(target = "bathRoomCount", source = "home.homeInfo.bathRoomCount")
    @Mapping(target = "type", source = "home.homeInfo.type")
    @Mapping(target = "homeStatus", source = "home.homeStatus")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.nickname")
    HomeDocument homeToHomeDocument(Home home, User user);


    @Mapping(target = "id", source = "homeDocument.id")
    @Mapping(target = "address", source = "homeDocument.address")
    @Mapping(target = "latitude", source = "homeDocument.latitude")
    @Mapping(target = "longitude", source = "homeDocument.longitude")
    @Mapping(target = "mainImage", source = "homeDocument.mainImage")
    @Mapping(target = "rent", source = "homeDocument.rent")
    @Mapping(target = "bond", source = "homeDocument.bond")
    @Mapping(target = "bill", source = "homeDocument.bill")
    @Mapping(target = "bedroomCount", source = "homeDocument.bedRoomCount")
    @Mapping(target = "bathRoomCount", source = "homeDocument.bathRoomCount")
    @Mapping(target = "type", source = "homeDocument.type")
    @Mapping(target = "homeStatus", source = "homeDocument.homeStatus")
    @Mapping(target = "userIdx", source = "homeDocument.id")
    @Mapping(target = "userName", source = "homeDocument.userName")
    HomeOverviewResponse homeDocumentToHomeOverviewResponse(HomeDocument homeDocument);

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
