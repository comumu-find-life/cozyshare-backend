package com.core.home.dto;

import com.core.home.model.HomeType;
import com.core.user.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HomeUpdateRequest {
    private Long homeId;
    private HomeAddressGeneratorRequest homeAddress;
    private Integer bathRoomCount;
    private boolean dealSavable;
    private Integer bedroomCount;
    private Integer bathroomType;
    private Integer bond;
    private Gender gender;
    private HomeType type;
    private String introduce;
    private Integer bill;
    private Integer rent;
    private String options;
    private Integer residentCount;
    private boolean canParking;
}
