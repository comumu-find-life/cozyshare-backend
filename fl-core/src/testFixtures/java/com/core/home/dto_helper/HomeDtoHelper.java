package com.core.home.dto_helper;

import com.core.domain.home.dto.HomeAddressGeneratorRequest;
import com.core.domain.home.dto.HomeGeneratorRequest;
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
                .state("NSW")
                .city("city")
                .streetName("street")
                .detailAddress("401ho")
                .streetCode("2000")
                .latitude(100)
                .longitude(100)
                .build();
    }
}
