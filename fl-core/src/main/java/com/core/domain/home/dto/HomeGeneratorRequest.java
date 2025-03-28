package com.core.domain.home.dto;

import com.core.domain.home.model.HomeType;
import com.core.domain.user.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HomeGeneratorRequest {
    private HomeAddressGeneratorRequest homeAddress;
    private Integer rent;
    private Integer bond;
    private Integer bill;
    private Integer bedroomCount;
    private Integer bathRoomCount;
    private Integer residentCount;
    private boolean dealSavable;
    private boolean canParking;
    private String introduce;
    private Gender gender;
    private HomeType type;
    private String options;

}