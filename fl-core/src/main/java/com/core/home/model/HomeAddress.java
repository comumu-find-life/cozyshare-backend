package com.core.home.model;

import com.core.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "home_address", indexes = {@Index(name = "idx_city", columnList = "city")})
public class HomeAddress extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "home_address_id", nullable = false)
    private Long id;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "post_code", nullable = false)
    private Integer postCode;

    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    @Column(name = "street_name", nullable = false)
    private String streetName;

    @Column(name = "street_code", nullable = false)
    private String streetCode;

    @Column(name = "latitude", nullable = true)
    private double latitude;

    @Column(name = "longitude", nullable = true)
    private double longitude;

    protected void setLatLnd(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String parseAddress(){
        StringBuilder sb = new StringBuilder();
        sb.append(streetCode+" ");
        sb.append(streetName+",");
        sb.append(city+" ");
        sb.append(state+" ");
        sb.append(postCode);
        return sb.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HomeAddress)) return false;
        HomeAddress that = (HomeAddress) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}