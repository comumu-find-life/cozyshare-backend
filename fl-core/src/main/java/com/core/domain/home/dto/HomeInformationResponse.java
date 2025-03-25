package com.core.domain.home.dto;

import com.core.domain.home.model.HomeStatus;
import com.core.domain.home.model.HomeType;
import com.core.domain.user.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeInformationResponse {
    private String providerId;

    private Long homeId;

    private String providerProfileUrl;

    private String providerName;

    private String address;

    private List<String> images;

    private Integer bond;

    private Integer bill;

    private Integer rent;

    private HomeType type;

    private Gender gender;

    private String introduce;

    private String options;

    private Integer bathRoomCount;

    private Integer bedroomCount;

    private Integer residentCount;

    private double latitude;

    private double longitude;

    private boolean canParking;

    private HomeStatus homeStatus;
}
