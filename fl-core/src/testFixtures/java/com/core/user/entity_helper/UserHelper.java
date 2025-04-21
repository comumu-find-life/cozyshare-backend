package com.core.user.entity_helper;

import com.core.user.model.SignupType;
import com.core.user.model.User;
import com.core.user.model.UserAccount;

import java.util.ArrayList;

public class UserHelper {
    public static User generateUser(Long id) {
        return User.builder()
                .id(id)
                .email(id + "test123@test.com")
                .nickname("user" + id)
                .password("123123")
                .fcmToken("token")
                .signupType(SignupType.APPLE)
                .build();
    }

    public static UserAccount generateUserAccount(Long userId, double point){
        return UserAccount.builder()
                .userId(userId)
                .point(point)
                .depositorName("name")
                .chargeHistories(new ArrayList<>())
                .paypalInformation("01083131764")
                .build();
    }
}
