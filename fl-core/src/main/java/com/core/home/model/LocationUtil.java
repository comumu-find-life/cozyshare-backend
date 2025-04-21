package com.core.home.model;


import com.core.home.dto.HomeAddressGeneratorRequest;

public class LocationUtil {
    static public String toStringAddress(HomeAddressGeneratorRequest addressDto){
        StringBuilder sb = new StringBuilder();
        sb.append(addressDto.getStreetCode() + " ");
        sb.append(addressDto.getStreetName() + " ");
        sb.append(addressDto.getCity() + " ");
        sb.append(addressDto.getState()+",");
        sb.append(addressDto.getPostCode()+" ");
        sb.append("Australia");
        return sb.toString();
    }
}
