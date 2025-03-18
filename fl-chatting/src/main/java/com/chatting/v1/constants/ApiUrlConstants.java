package com.chatting.v1.constants;

public class ApiUrlConstants {
    public static final String BASE_API_URL = "/v1/api";
    public static final String DM_SEND_FIRST_URL = BASE_API_URL + "/direct-messages";
    public static final String DM_HISTORY_URL = BASE_API_URL + "/direct-messages/history";
    public static final String DM_CHECK_READ_URL = BASE_API_URL + "/direct-messages/read";
    public static final String DM_FIND_ALL_ROOMS = BASE_API_URL + "/users/{userId}/direct-message-rooms";
    public static final String DM_SEND_IMAGE_URL = BASE_API_URL + "/direct-messages/image";
}
