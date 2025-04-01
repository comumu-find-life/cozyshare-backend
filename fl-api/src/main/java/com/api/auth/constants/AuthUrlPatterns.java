package com.api.auth.constants;


import static com.api.v1.constants.ApiUrlConstants.V1_BASE_API_URL;

public final class AuthUrlPatterns {

    public static final String[] GET_AUTH_WHITELIST = {
            V1_BASE_API_URL + "/homes",
            V1_BASE_API_URL + "/homes/**",
            V1_BASE_API_URL + "/homes/city",
            V1_BASE_API_URL + "/homes/{homeId}",
            V1_BASE_API_URL + "/homes/users/{userId}",
            V1_BASE_API_URL + "/dm",
            V1_BASE_API_URL + "/dm/**",
            V1_BASE_API_URL + "/homes/overview",
            V1_BASE_API_URL + "/users/profile/**",
            V1_BASE_API_URL + "/users/verification/email/**",
    };

    public static final String[] POST_AUTH_WHITELIST = {
            V1_BASE_API_URL + "/google",
            V1_BASE_API_URL + "/apple",
            V1_BASE_API_URL + "/users/profile/{userId}",
            V1_BASE_API_URL + "/users/sign-up",
            V1_BASE_API_URL + "/users/sign-up/google",
            V1_BASE_API_URL + "/users/login",
            V1_BASE_API_URL + "/users/verification/email/send/**",
            V1_BASE_API_URL + "/users/verification/email/confirm/**",
    };
}