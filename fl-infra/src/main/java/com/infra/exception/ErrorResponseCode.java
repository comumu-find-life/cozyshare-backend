package com.infra.exception;

public enum ErrorResponseCode {
    NOT_VALID_TOKEN(4011),
    FAIL_SEND_TOKEN(4012),
    FAIL_LOGIN(4013),
    NOT_FOUND(4041),
    INVALID_DATA(4042),
    NOT_MATCH_ID(4043),
    UPLOAD_S3_ERROR(5004),
    FCM_PUSH(5005),
    GOOGLE_LOCATION(5006),
    POINT_INSUFFICIENT(5007),
    OAUTH_INVALID(5009);

    private final int code;

    ErrorResponseCode(int c) {
        this.code = c;
    }

    public int getCode() {
        return this.code;
    }
}