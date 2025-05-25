package com.core.home.model;

import com.core.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;

import java.util.List;


@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "home", indexes = {
        @Index(name = "idx_home_user_status", columnList = "home_id, user_id, home_status"),
        @Index(name = "idx_home_home_status", columnList = "home_status"),
        @Index(name = "idx_home_user", columnList = "user_id")
})
public class Home extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "home_id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @JsonIgnore
    @OneToMany(mappedBy = "home", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<HomeImage> images;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "home_address_id")
    private HomeAddress homeAddress;

    @Embedded
    private HomeInfo homeInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "home_status")
    private HomeStatus homeStatus;

    public void addImages(List<HomeImage> images){
        this.images.addAll(images);
    }

    public String getMainImage(){
        return images.get(0).getImageUrl();
    }

    public void setLatLng(double lat, double lng) {
        homeAddress.setLatLnd(lat, lng);
    }

    public void setStatus(HomeStatus status) {
        this.homeStatus = status;
    }

    public void setImages(List<HomeImage> images) {
        this.images = images;
    }
}