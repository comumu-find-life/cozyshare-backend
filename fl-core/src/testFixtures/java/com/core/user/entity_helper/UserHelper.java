package com.core.user.entity_helper;

import com.core.domain.user.model.SignupType;
import com.core.domain.user.model.User;

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
}
