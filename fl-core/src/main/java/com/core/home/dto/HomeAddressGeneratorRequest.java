package com.core.home.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeAddressGeneratorRequest {
    private Integer postCode;
    private String state;
    private String city;
    private String streetName;
    private String detailAddress;
    private String streetCode;
    private double latitude;
    private double longitude;
}
