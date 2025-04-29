//package com.core.home.model;
//
//import lombok.*;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//
//@Document(indexName = "home")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class HomeDocument {
//
//    @Id
//    private Long id;
//
//    @Field(name = "address")
//    private String address;
//
//    @Field(name = "latitude")
//    private double latitude;
//
//    @Field(name = "longitude")
//    private double longitude;
//
//    @Field(name = "main_image")
//    private String mainImage;
//
//    @Field(name = "rent")
//    private Integer rent;
//
//    @Field(name = "bond")
//    private Integer bond;
//
//    @Field(name = "bill")
//    private Integer bill;
//
//    @Field(name = "bed_room_count")
//    private Integer bedRoomCount;
//
//    @Field(name = "bath_room_count")
//    private Integer bathRoomCount;
//
//    @Field(name = "type")
//    private String type;
//
//    @Field(name = "user_id")
//    private Long userId;
//
//    @Field(name = "user_name")
//    private String userName;
//
//    @Setter
//    @Field(name = "home_status")
//    private HomeStatus homeStatus;
//
//}
