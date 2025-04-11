package com.core.mapper;

import com.core.domain.home.dto.HomeAddressGeneratorRequest;
import com.core.domain.home.dto.HomeGeneratorRequest;
import com.core.domain.home.dto.HomeInformationResponse;
import com.core.domain.home.dto.HomeOverviewResponse;
import com.core.domain.home.dto.HomeUpdateRequest;
import com.core.domain.home.model.Home;
import com.core.domain.home.model.HomeAddress;
import com.core.domain.home.model.HomeInfo;
import com.core.domain.home.model.HomeType;
import com.core.domain.user.model.Gender;
import com.core.domain.user.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-11T17:54:45+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class HomeMapperImpl implements HomeMapper {

    @Override
    public Home toEntity(HomeGeneratorRequest homeDto, Long userId) {
        if ( homeDto == null && userId == null ) {
            return null;
        }

        Home.HomeBuilder home = Home.builder();

        if ( homeDto != null ) {
            home.homeInfo( mapHomeInfo( homeDto ) );
            home.homeAddress( toAddressEntity( homeDto.getHomeAddress() ) );
        }
        home.userId( userId );
        home.homeStatus( com.core.domain.home.model.HomeStatus.FOR_SALE );

        return home.build();
    }

    @Override
    public HomeAddress toAddressEntity(HomeAddressGeneratorRequest homeAddressDto) {
        if ( homeAddressDto == null ) {
            return null;
        }

        HomeAddress.HomeAddressBuilder homeAddress = HomeAddress.builder();

        homeAddress.state( homeAddressDto.getState() );
        homeAddress.city( homeAddressDto.getCity() );
        homeAddress.postCode( homeAddressDto.getPostCode() );
        homeAddress.detailAddress( homeAddressDto.getDetailAddress() );
        homeAddress.streetName( homeAddressDto.getStreetName() );
        homeAddress.streetCode( homeAddressDto.getStreetCode() );

        return homeAddress.build();
    }

    @Override
    public void updateHomeFromDto(HomeUpdateRequest dto, HomeInfo entity) {
        if ( dto == null ) {
            return;
        }

        entity.setCanParking( dto.isCanParking() );
        if ( dto.getBathRoomCount() != null ) {
            entity.setBathRoomCount( dto.getBathRoomCount() );
        }
        if ( dto.getBedroomCount() != null ) {
            entity.setBedroomCount( dto.getBedroomCount() );
        }
        entity.setResidentCount( dto.getResidentCount() );
        entity.setOptions( dto.getOptions() );
        entity.setBond( dto.getBond() );
        entity.setIntroduce( dto.getIntroduce() );
        entity.setBill( dto.getBill() );
        entity.setRent( dto.getRent() );
        entity.setGender( dto.getGender() );
        entity.setType( dto.getType() );
    }

    @Override
    public void updateAddressFromDto(HomeAddressGeneratorRequest dto, HomeAddress entity) {
        if ( dto == null ) {
            return;
        }

        entity.setState( dto.getState() );
        entity.setCity( dto.getCity() );
        entity.setPostCode( dto.getPostCode() );
        entity.setDetailAddress( dto.getDetailAddress() );
        entity.setStreetName( dto.getStreetName() );
        entity.setStreetCode( dto.getStreetCode() );
        entity.setLatitude( dto.getLatitude() );
        entity.setLongitude( dto.getLongitude() );
    }

    @Override
    public HomeInformationResponse toHomeInformation(Home home, User user) {
        if ( home == null && user == null ) {
            return null;
        }

        HomeInformationResponse.HomeInformationResponseBuilder homeInformationResponse = HomeInformationResponse.builder();

        if ( home != null ) {
            homeInformationResponse.homeId( home.getId() );
            homeInformationResponse.latitude( homeHomeAddressLatitude( home ) );
            homeInformationResponse.longitude( homeHomeAddressLongitude( home ) );
            homeInformationResponse.introduce( homeHomeInfoIntroduce( home ) );
            homeInformationResponse.rent( homeHomeInfoRent( home ) );
            homeInformationResponse.bond( homeHomeInfoBond( home ) );
            homeInformationResponse.gender( homeHomeInfoGender( home ) );
            homeInformationResponse.type( homeHomeInfoType( home ) );
            homeInformationResponse.bill( homeHomeInfoBill( home ) );
            homeInformationResponse.bedroomCount( homeHomeInfoBedroomCount( home ) );
            homeInformationResponse.bathRoomCount( homeHomeInfoBathRoomCount( home ) );
            homeInformationResponse.residentCount( homeHomeInfoResidentCount( home ) );
            homeInformationResponse.options( homeHomeInfoOptions( home ) );
            homeInformationResponse.address( mapSimpleAddress( home.getHomeAddress() ) );
            homeInformationResponse.images( mapImageUrls( home.getImages() ) );
            homeInformationResponse.canParking( homeHomeInfoCanParking( home ) );
            homeInformationResponse.homeStatus( home.getHomeStatus() );
        }
        if ( user != null ) {
            if ( user.getId() != null ) {
                homeInformationResponse.providerId( String.valueOf( user.getId() ) );
            }
            homeInformationResponse.providerProfileUrl( user.getProfileUrl() );
            homeInformationResponse.providerName( user.getNickname() );
        }

        return homeInformationResponse.build();
    }

    @Override
    public HomeOverviewResponse toSimpleHomeDto(Home home, User user) {
        if ( home == null && user == null ) {
            return null;
        }

        HomeOverviewResponse.HomeOverviewResponseBuilder homeOverviewResponse = HomeOverviewResponse.builder();

        if ( home != null ) {
            homeOverviewResponse.id( home.getId() );
            homeOverviewResponse.address( mapSimpleAddress( home.getHomeAddress() ) );
            homeOverviewResponse.latitude( homeHomeAddressLatitude( home ) );
            homeOverviewResponse.longitude( homeHomeAddressLongitude( home ) );
            homeOverviewResponse.mainImage( mapMainImage( home.getImages() ) );
            homeOverviewResponse.rent( homeHomeInfoRent( home ) );
            homeOverviewResponse.bond( homeHomeInfoBond( home ) );
            homeOverviewResponse.bill( homeHomeInfoBill( home ) );
            homeOverviewResponse.bedroomCount( homeHomeInfoBedroomCount( home ) );
            homeOverviewResponse.bathRoomCount( homeHomeInfoBathRoomCount( home ) );
            HomeType type = homeHomeInfoType( home );
            if ( type != null ) {
                homeOverviewResponse.type( type.name() );
            }
            homeOverviewResponse.homeStatus( home.getHomeStatus() );
        }
        if ( user != null ) {
            homeOverviewResponse.userIdx( user.getId() );
            homeOverviewResponse.userName( user.getNickname() );
        }

        return homeOverviewResponse.build();
    }

    private double homeHomeAddressLatitude(Home home) {
        if ( home == null ) {
            return 0.0d;
        }
        HomeAddress homeAddress = home.getHomeAddress();
        if ( homeAddress == null ) {
            return 0.0d;
        }
        double latitude = homeAddress.getLatitude();
        return latitude;
    }

    private double homeHomeAddressLongitude(Home home) {
        if ( home == null ) {
            return 0.0d;
        }
        HomeAddress homeAddress = home.getHomeAddress();
        if ( homeAddress == null ) {
            return 0.0d;
        }
        double longitude = homeAddress.getLongitude();
        return longitude;
    }

    private String homeHomeInfoIntroduce(Home home) {
        if ( home == null ) {
            return null;
        }
        HomeInfo homeInfo = home.getHomeInfo();
        if ( homeInfo == null ) {
            return null;
        }
        String introduce = homeInfo.getIntroduce();
        if ( introduce == null ) {
            return null;
        }
        return introduce;
    }

    private Integer homeHomeInfoRent(Home home) {
        if ( home == null ) {
            return null;
        }
        HomeInfo homeInfo = home.getHomeInfo();
        if ( homeInfo == null ) {
            return null;
        }
        Integer rent = homeInfo.getRent();
        if ( rent == null ) {
            return null;
        }
        return rent;
    }

    private Integer homeHomeInfoBond(Home home) {
        if ( home == null ) {
            return null;
        }
        HomeInfo homeInfo = home.getHomeInfo();
        if ( homeInfo == null ) {
            return null;
        }
        Integer bond = homeInfo.getBond();
        if ( bond == null ) {
            return null;
        }
        return bond;
    }

    private Gender homeHomeInfoGender(Home home) {
        if ( home == null ) {
            return null;
        }
        HomeInfo homeInfo = home.getHomeInfo();
        if ( homeInfo == null ) {
            return null;
        }
        Gender gender = homeInfo.getGender();
        if ( gender == null ) {
            return null;
        }
        return gender;
    }

    private HomeType homeHomeInfoType(Home home) {
        if ( home == null ) {
            return null;
        }
        HomeInfo homeInfo = home.getHomeInfo();
        if ( homeInfo == null ) {
            return null;
        }
        HomeType type = homeInfo.getType();
        if ( type == null ) {
            return null;
        }
        return type;
    }

    private Integer homeHomeInfoBill(Home home) {
        if ( home == null ) {
            return null;
        }
        HomeInfo homeInfo = home.getHomeInfo();
        if ( homeInfo == null ) {
            return null;
        }
        Integer bill = homeInfo.getBill();
        if ( bill == null ) {
            return null;
        }
        return bill;
    }

    private Integer homeHomeInfoBedroomCount(Home home) {
        if ( home == null ) {
            return null;
        }
        HomeInfo homeInfo = home.getHomeInfo();
        if ( homeInfo == null ) {
            return null;
        }
        int bedroomCount = homeInfo.getBedroomCount();
        return bedroomCount;
    }

    private Integer homeHomeInfoBathRoomCount(Home home) {
        if ( home == null ) {
            return null;
        }
        HomeInfo homeInfo = home.getHomeInfo();
        if ( homeInfo == null ) {
            return null;
        }
        int bathRoomCount = homeInfo.getBathRoomCount();
        return bathRoomCount;
    }

    private Integer homeHomeInfoResidentCount(Home home) {
        if ( home == null ) {
            return null;
        }
        HomeInfo homeInfo = home.getHomeInfo();
        if ( homeInfo == null ) {
            return null;
        }
        Integer residentCount = homeInfo.getResidentCount();
        if ( residentCount == null ) {
            return null;
        }
        return residentCount;
    }

    private String homeHomeInfoOptions(Home home) {
        if ( home == null ) {
            return null;
        }
        HomeInfo homeInfo = home.getHomeInfo();
        if ( homeInfo == null ) {
            return null;
        }
        String options = homeInfo.getOptions();
        if ( options == null ) {
            return null;
        }
        return options;
    }

    private boolean homeHomeInfoCanParking(Home home) {
        if ( home == null ) {
            return false;
        }
        HomeInfo homeInfo = home.getHomeInfo();
        if ( homeInfo == null ) {
            return false;
        }
        boolean canParking = homeInfo.isCanParking();
        return canParking;
    }
}
