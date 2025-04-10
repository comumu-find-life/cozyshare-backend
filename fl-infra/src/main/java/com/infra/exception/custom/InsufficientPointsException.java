package com.infra.exception.custom;

import androidx.annotation.Nullable;
import com.infra.exception.ErrorResponseCode;
import com.infra.exception.ExceptionBase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InsufficientPointsException extends ExceptionBase {

    public InsufficientPointsException(@Nullable String message) {
        this.errorCode = ErrorResponseCode.POINT_INSUFFICIENT;
        this.errorMessage = message;
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
