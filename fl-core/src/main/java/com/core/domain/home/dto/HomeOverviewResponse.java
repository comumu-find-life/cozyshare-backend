package com.core.domain.home.dto;

import com.core.domain.home.model.HomeStatus;
import lombok.*;

/**
 * 제거 예정 HomeListOvervireResponse로 대체
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomeOverviewResponse {
        private Long id;
        private String address;
        private double latitude;
        private double longitude;
        private String mainImage;
        private Integer rent;
        private Integer bond;
        private Integer bill;
        private Integer bedroomCount;
        private Integer bathRoomCount;
        private String type;
        private Long userIdx;
        private String userName;
        private HomeStatus homeStatus;
}