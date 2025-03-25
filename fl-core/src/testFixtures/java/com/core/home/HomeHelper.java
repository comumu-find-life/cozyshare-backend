package com.core.home;

import com.core.domain.home.model.*;
import com.core.domain.user.model.Gender;

import java.util.ArrayList;
import java.util.List;

public class HomeHelper {

    public static Home generateHome(Long userId){
        Home home = Home.builder()
                .userId(userId)
                .homeAddress(generateHomeAddress())
                .homeInfo(generateHomeInfo())
                .homeStatus(HomeStatus.FOR_SALE)
                .build();
        home.setImages(generateHomeImages(home));
        return home;
    }

    private static HomeInfo generateHomeInfo(){
        return HomeInfo.builder()
                .canParking(true)
                .bathRoomCount(2)
                .bedroomCount(3)
                .residentCount(4)
                .type(HomeType.HOME_STAY)
                .options("A_S,DESK,CHAIR")
                .bond(1000)
                .introduce("A cozy and modern home.")
                .bill(200)
                .rent(1500)
                .gender(Gender.FEMALE)
                .build();
    }

    private static HomeAddress generateHomeAddress(){
        return HomeAddress.builder()
                .state("NSW")
                .city("cityName")
                .postCode(2000)
                .detailAddress("201호")
                .streetName("street")
                .streetCode("123")
                .latitude(35.123)
                .longitude(164.123)
                .build();
    }

    private static List<HomeImage> generateHomeImages(Home home){
        List<HomeImage> images = new ArrayList<>();
        for(int i=0; i<3; i++){
            HomeImage build = HomeImage.builder()
                    .imageUrl("IMAGE" + i)
                    .home(home)  // Home 설정 추가
                    .build();
            images.add(build);
        }
        return images;
    }
}
