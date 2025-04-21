package com.core.home.model;

public enum HomeStatus {
    SOLD_OUT("판매 완료"),
    SELL_STOP("판매 중단"),
    DURING_SELL("임차인과 거래중"),
    FOR_SALE("판매 중");

    private final String description;

    HomeStatus(String description) {
        this.description = description;
    }

    public static HomeStatus fromString(String status) {
        for (HomeStatus homeStatus : HomeStatus.values()) {
            if (homeStatus.name().equalsIgnoreCase(status)) {
                return homeStatus;
            }
        }
        return null;
    }


}