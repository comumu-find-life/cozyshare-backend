package com.core.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UserUpdatePointRequest {
    private Long userId;
    private double point;
}
