package com.core.user.dto;

import com.core.user.model.Gender;
import com.core.user.model.SignupType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Admin 이 사용자를 조회할 데이터를 담은 DTO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInformationByAdminResponse {

    private Long id;

    private String email;

    private String nickname;

    private String profileUrl;

    private Integer brith;

    private Integer phoneNumber;

    private String job;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private SignupType signupType;

    private String nationality;
    //자기 소개글
    private String introduce;


}
