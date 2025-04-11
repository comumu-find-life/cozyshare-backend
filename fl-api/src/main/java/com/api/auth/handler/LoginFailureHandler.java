package com.api.auth.handler;

import com.infra.exception.custom.AuthException;
import com.infra.exception.ErrorResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import static com.api.auth.constants.AuthConstants.FAIL_TO_LOGIN;


@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception){
        throw new AuthException(ErrorResponseCode.FAIL_LOGIN, FAIL_TO_LOGIN);
    }
}