package com.core.user.dto;

import com.core.user.model.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {

    private Long id;

    private String nickname;

    private String profileUrl;

    private String job;

    private Gender gender;

    private String nationality;

    private String introduce;
}
