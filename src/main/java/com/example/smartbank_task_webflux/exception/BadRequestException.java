package com.example.smartbank_task_webflux.exception;

import com.example.smartbank_task_webflux.exception.handler.ApiErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ApiErrorMessages.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    public BadRequestException() {
        this(ApiErrorMessages.BAD_REQUEST);
    }

    public BadRequestException(String s) {
        super(s);
    }
}
