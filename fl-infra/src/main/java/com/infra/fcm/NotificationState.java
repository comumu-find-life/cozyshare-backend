package com.infra.fcm;

public enum NotificationState {
    SAVE("save"),
    NOT_SAVE("notSave");

    private final String value;

    NotificationState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
