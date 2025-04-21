package com.core.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInformationResponse {

    private Long id;

    private String email;

    private String nickname;

    private String profileUrl;

}
