package com.api.exception;

import com.infra.exception.*;
import com.infra.exception.custom.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;

@ControllerAdvice
public class ExceptionResolver {
    private static final Logger logger = LogManager.getLogger(ExceptionResolver.class);

    /**
     * 클라이언트쪽 에러 발생
     */
    @ExceptionHandler({AuthException.class, NotFoundDataException.class, InvalidDataException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleClientException(Exception exception) {
        return new ErrorResponse((ExceptionBase) exception);
    }

    /**
     * 서버 오류 발생
     */
    @ExceptionHandler({
            InsufficientPointsException.class,
            GoogleLocationException.class,
            FcmException.class,
            S3UploadException.class,
            InvalidOAuthTokenException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleServerException(HttpServletRequest request, Exception exception) {
        // 서버 오류만 로그 기록
        logException(request, (ExceptionBase) exception);
        return new ErrorResponse((ExceptionBase) exception);
    }

    private void logException(HttpServletRequest request, ExceptionBase exception) {
        String username = getAuthenticatedUsername();
        String requestBody = extractRequestBody(request);
        logger.error("Request URL: {}, Method: {}, Username: {}, Exception: {}, Request Body: {}",
                request.getRequestURL(),
                request.getMethod(),
                username,
                exception.getErrorMessage(),
                requestBody);
    }

    private String extractRequestBody(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper wrapper) {
            byte[] content = wrapper.getContentAsByteArray();
            if (content.length > 0) {
                return new String(content, StandardCharsets.UTF_8);
            }
        }
        return "N/A";
    }

    private String getAuthenticatedUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.isAuthenticated()) ? auth.getName() : "Anonymous";
    }
}
