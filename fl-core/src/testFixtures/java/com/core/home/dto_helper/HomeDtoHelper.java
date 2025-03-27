package com.core.home.dto_helper;

import com.core.domain.home.dto.HomeAddressGeneratorRequest;
import com.core.domain.home.dto.HomeGeneratorRequest;
import com.core.domain.home.dto.HomeUpdateRequest;
import com.core.domain.home.model.HomeType;
import com.core.domain.user.model.Gender;

public class HomeDtoHelper {



    public static HomeGeneratorRequest generateHomeGeneratorRequest() {
        return HomeGeneratorRequest.builder()
                .homeAddress(generateHomeAddressGeneratorRequest())
                .rent(1000)
                .bond(20000)
                .bill(10)
                .gender(Gender.FEMALE)
                .bedroomCount(10)
                .bathRoomCount(10)
                .residentCount(10)
                .dealSavable(true)
                .canParking(true)
                .introduce("introduce")
                .type(HomeType.HOME_STAY)
                .options("")
                .build();
    }

    public static HomeAddressGeneratorRequest generateHomeAddressGeneratorRequest() {
        return HomeAddressGeneratorRequest.builder()
                .postCode(2000)
                .state("3000")
                .city("Melbourne")
                .streetName("Collins St")
                .detailAddress("401ho")
                .streetCode("123")
                .latitude(100)
                .longitude(100)
                .build();
    }

    public static HomeUpdateRequest generateHomeUpdateRequest(Long homeId) {
        return HomeUpdateRequest.builder()
                .homeId(homeId)
                .homeAddress(generateHomeAddressGeneratorRequest())
                .bathRoomCount(100)
                .dealSavable(true)
                .bedroomCount(100)
                .bathroomType(1)
                .bond(100)
                .gender(Gender.ANYTHING)
                .type(HomeType.HOME_STAY)
                .introduce("introduceee")
                .bill(100)
                .rent(100)
                .options("")
                .residentCount(100)
                .canParking(false)
                .build();
    }
}
