package com.infra.exception;

import androidx.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class NotMatchGetterException extends ExceptionBase{

    public NotMatchGetterException(@Nullable String message) {
        this.errorCode = ErrorResponseCode.NOT_MATCH_ID;
        this.errorMessage = message;
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_ACCEPTABLE.value();
    }
}
