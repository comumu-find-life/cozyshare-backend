package com.core.user.dto;

import com.core.user.model.Gender;
import lombok.Builder;
import lombok.Getter;

/**
 * 사용자가 다른 사용자 프로필을 조회할때 사용될 DTO
 */
@Getter
@Builder
public class UserProfileRequest {

    private String nickName;

    private String profileUrl;

    private String job;

    private Gender gender;

    private String nationality;
}
