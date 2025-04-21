package com.core.home.service;

import com.core.home.dto.HomeAddressGeneratorRequest;
import com.core.home.model.LatLng;

public interface LocationService {
    LatLng getLatLngFromAddress(final HomeAddressGeneratorRequest homeAddressDto) throws IllegalAccessException;
}
