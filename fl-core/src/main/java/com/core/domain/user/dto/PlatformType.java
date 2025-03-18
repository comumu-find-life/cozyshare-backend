package com.core.domain.user.dto;

public enum PlatformType {
    ANDROID,
    IOS;
    public boolean isAndroid(){
        return this == ANDROID;
    }
}
