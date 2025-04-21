package com.core.home.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "home")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeDocument {

    @Id
    private Long id;

    private String address;

    private double latitude;

    private double longitude;

    @Field(name = "main_image")
    private String mainImage;

    private Integer rent;

    private Integer bond;

    private Integer bill;

    @Field(name = "bed_room_count")
    private Integer bedRoomCount;

    @Field(name = "bath_room_count")
    private Integer bathRoomCount;

    private String type;

    @Field(name = "user_id")
    private Long userId;

    @Field(name = "user_name")
    private String userName;

    private String homeStatus;
}
