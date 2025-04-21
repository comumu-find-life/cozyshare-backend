package com.core.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
class LoginResponse{
    String accessToken;
    String refreshToken;

}