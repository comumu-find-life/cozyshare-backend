package com.core.mapper;

import com.core.domain.user.dto.UserAccountRequest;
import com.core.domain.user.dto.UserAccountResponse;
import com.core.domain.user.dto.UserInformationByAdminResponse;
import com.core.domain.user.dto.UserInformationResponse;
import com.core.domain.user.dto.UserProfileResponse;
import com.core.domain.user.dto.UserProfileUpdateRequest;
import com.core.domain.user.dto.UserSignupRequest;
import com.core.domain.user.model.User;
import com.core.domain.user.model.UserAccount;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-03T02:13:03+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserSignupRequest dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( dto.getEmail() );
        user.nickname( dto.getNickname() );
        user.password( dto.getPassword() );
        user.fcmToken( dto.getFcmToken() );
        user.job( dto.getJob() );
        user.gender( dto.getGender() );
        user.signupType( dto.getSignupType() );
        user.nationality( dto.getNationality() );
        user.introduce( dto.getIntroduce() );

        user.userState( com.core.domain.user.model.UserState.ACTIVE );

        return user.build();
    }

    @Override
    public UserInformationResponse toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserInformationResponse.UserInformationResponseBuilder userInformationResponse = UserInformationResponse.builder();

        userInformationResponse.id( user.getId() );
        userInformationResponse.email( user.getEmail() );
        userInformationResponse.nickname( user.getNickname() );
        userInformationResponse.profileUrl( user.getProfileUrl() );

        return userInformationResponse.build();
    }

    @Override
    public UserInformationByAdminResponse toAdminResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserInformationByAdminResponse.UserInformationByAdminResponseBuilder userInformationByAdminResponse = UserInformationByAdminResponse.builder();

        userInformationByAdminResponse.id( user.getId() );
        userInformationByAdminResponse.email( user.getEmail() );
        userInformationByAdminResponse.nickname( user.getNickname() );
        userInformationByAdminResponse.profileUrl( user.getProfileUrl() );
        userInformationByAdminResponse.job( user.getJob() );
        userInformationByAdminResponse.gender( user.getGender() );
        userInformationByAdminResponse.signupType( user.getSignupType() );
        userInformationByAdminResponse.nationality( user.getNationality() );
        userInformationByAdminResponse.introduce( user.getIntroduce() );

        return userInformationByAdminResponse.build();
    }

    @Override
    public UserProfileResponse toProfile(User user) {
        if ( user == null ) {
            return null;
        }

        UserProfileResponse.UserProfileResponseBuilder userProfileResponse = UserProfileResponse.builder();

        userProfileResponse.id( user.getId() );
        userProfileResponse.nickname( user.getNickname() );
        userProfileResponse.profileUrl( user.getProfileUrl() );
        userProfileResponse.job( user.getJob() );
        userProfileResponse.gender( user.getGender() );
        userProfileResponse.nationality( user.getNationality() );
        userProfileResponse.introduce( user.getIntroduce() );

        return userProfileResponse.build();
    }

    @Override
    public UserAccount toUserAccount(UserAccountRequest userAccountRequest, Long userId) {
        if ( userAccountRequest == null && userId == null ) {
            return null;
        }

        UserAccount.UserAccountBuilder userAccount = UserAccount.builder();

        if ( userAccountRequest != null ) {
            userAccount.depositorName( userAccountRequest.getDepositorName() );
            userAccount.paypalInformation( userAccountRequest.getPaypalInformation() );
        }
        userAccount.userId( userId );
        userAccount.point( 0.0 );

        return userAccount.build();
    }

    @Override
    public UserAccountResponse toUserAccountResponse(UserAccount userAccount) {
        if ( userAccount == null ) {
            return null;
        }

        UserAccountResponse.UserAccountResponseBuilder userAccountResponse = UserAccountResponse.builder();

        userAccountResponse.chargeHistories( mapChargeHistories( userAccount.getChargeHistories() ) );
        userAccountResponse.depositorName( userAccount.getDepositorName() );
        userAccountResponse.paypalInformation( userAccount.getPaypalInformation() );
        userAccountResponse.point( userAccount.getPoint() );

        return userAccountResponse.build();
    }

    @Override
    public void updateUser(UserProfileUpdateRequest userProfileUpdateRequest, User user) {
        if ( userProfileUpdateRequest == null ) {
            return;
        }

        user.setNickname( userProfileUpdateRequest.getNickname() );
        user.setJob( userProfileUpdateRequest.getJob() );
        user.setGender( userProfileUpdateRequest.getGender() );
        user.setIntroduce( userProfileUpdateRequest.getIntroduce() );
    }

    @Override
    public void updateUserAccount(UserAccountRequest userAccountRequest, UserAccount userAccount) {
        if ( userAccountRequest == null ) {
            return;
        }

        userAccount.setDepositorName( userAccountRequest.getDepositorName() );
        userAccount.setPaypalInformation( userAccountRequest.getPaypalInformation() );
    }
}
