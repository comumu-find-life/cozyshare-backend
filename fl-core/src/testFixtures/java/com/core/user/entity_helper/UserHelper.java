package com.core.user.entity_helper;

import com.core.domain.user.model.SignupType;
import com.core.domain.user.model.User;
import com.core.domain.user.model.UserAccount;

public class UserHelper {
    public static User generateUser(Long id) {
        return User.builder()
                .id(id)
                .email(id + "test123@test.com")
                .nickname("user" + id)
                .password("123123")
                .signupType(SignupType.APPLE)
                .build();
    }

    public static UserAccount generateUserAccount(Long userId, double point){
        return UserAccount.builder()
                .userId(userId)
                .point(point)
                .depositorName("name")
                .paypalInformation("01083131764")
                .build();
    }
}
