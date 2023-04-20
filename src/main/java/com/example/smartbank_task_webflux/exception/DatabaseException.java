package com.example.smartbank_task_webflux.exception;

import com.example.smartbank_task_webflux.exception.handler.ApiErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = ApiErrorMessages.INTERNAL_SERVER_ERROR)
public class DatabaseException extends RuntimeException {

    public DatabaseException() {
        this(ApiErrorMessages.INTERNAL_SERVER_ERROR);
    }

    public DatabaseException(String s) {
        super(s);
    }
}
