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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    HomeAddress toAddressEntity(HomeAddressGeneratorRequest homeAddressDto);

    @Mapping(target = "id", ignore = true)
    void updateAddressFromDto(HomeAddressGeneratorRequest dto, @MappingTarget HomeAddress entity);

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

    void updateHomeFromDto(HomeUpdateRequest dto, @MappingTarget HomeInfo entity);


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
