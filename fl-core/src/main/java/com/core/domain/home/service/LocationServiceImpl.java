package com.core.domain.home.service;

import com.core.domain.home.dto.HomeAddressGeneratorRequest;
import com.core.domain.home.model.LatLng;
import com.core.domain.home.model.LocationUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.infra.exception.custom.GoogleLocationException;
import com.infra.exception.custom.InvalidDataException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;


@Service
public class LocationServiceImpl implements LocationService {

    @Value("${map.api-url}")
    private String GEOCODING_API_URL;

    @Value("${map.api-key}")
    private String API_KEY;

    private static final String ADDRESS_PATTERN = "%s?address=%s&key=%s";
    private final RestTemplate restTemplate;

    @Autowired
    public LocationServiceImpl( ) {
        this.restTemplate = new RestTemplateBuilder().build();;
    }

    @Override
    public LatLng getLatLngFromAddress(HomeAddressGeneratorRequest homeAddressDto) {
        try {
            String encodedAddress = URLEncoder.encode(LocationUtil.toStringAddress(homeAddressDto), "UTF-8");
            String url = String.format(ADDRESS_PATTERN, GEOCODING_API_URL, encodedAddress, API_KEY);
            URI uri = URI.create(url);
            String response = restTemplate.getForObject(uri, String.class);
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
            validateAddress(jsonObject);
            JsonArray results = jsonObject.getAsJsonArray("results");
            for (JsonElement resultElem : results) {
                JsonObject result = resultElem.getAsJsonObject();
                String locationType = result
                        .getAsJsonObject("geometry")
                        .get("location_type").getAsString();

                if ("ROOFTOP".equals(locationType)) {
                    JsonObject location = result
                            .getAsJsonObject("geometry")
                            .getAsJsonObject("location");
                    double lat = location.get("lat").getAsDouble();
                    double lng = location.get("lng").getAsDouble();
                    return new LatLng(lat, lng);
                }
            }
            // "ROOFTOP" 결과가 없으면 예외
            throw new GoogleLocationException("정확한 주소 결과(ROOFTOP)를 찾을 수 없습니다.");

        } catch (Exception e) {
            throw new GoogleLocationException("주소 조회 실패");
        }
    }



    private void validateAddress(JsonObject jsonObject)  {
        if(jsonObject.size() == 0){
            throw new InvalidDataException("존재하지 않는 주소입니다.");
        }
    }
}
