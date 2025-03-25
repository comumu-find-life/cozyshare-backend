package com.core.domain.home.model;

import com.core.domain.user.model.Gender;
import jakarta.persistence.*;
import lombok.*;


@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeInfo {

    @Column(name = "can_parking", nullable = false)
    private boolean canParking;

    @Column(name = "bath_room_count", nullable = false)
    private int bathRoomCount;

    @Column(name = "bedroom_count", nullable = false)
    private int bedroomCount;

    @Column(name = "resident_count", nullable = false)
    private Integer residentCount;

    @Column(name = "options", nullable = false)
    private String options;

    @Column(name = "bond", nullable = false)
    private Integer bond;

    @Column(name = "introduce", nullable = false)
    private String introduce;

    @Column(name = "bill", nullable = false)
    private Integer bill;

    @Column(name = "rent", nullable = false)
    private Integer rent;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private HomeType type;


}
