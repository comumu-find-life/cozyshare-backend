package com.core.user.dto_helper;

import com.core.user.dto.UserInformationResponse;
import com.core.user.dto.UserSignupRequest;
import com.core.user.model.Gender;
import com.core.user.model.SignupType;

public class UserDtoHelper {

    public static UserSignupRequest generateUserSignupRequest(){
        return UserSignupRequest.builder()
                .fcmToken("fcmToken")
                .email("test123@test.com")
                .nickname("test user")
                .job("student")
                .brith(001031)
                .gender(Gender.MALE)
                .nationality("South Korea")
                .introduce("This is test user")
                .signupType(SignupType.APPLE)
                .build();
    }

    public static UserInformationResponse generateUserInformationResponse() {
        return UserInformationResponse.builder()
                .id(1L)
                .email("test123@test.com")
                .nickname("nickname")
                .profileUrl("")
                .build();
    }
}
