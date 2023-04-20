package com.example.smartbank_task_webflux.exception;

import com.example.smartbank_task_webflux.exception.handler.ApiErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = ApiErrorMessages.INTERNAL_SERVER_ERROR)
public class CustomInternalServerException extends RuntimeException {
    public CustomInternalServerException() {
        this(ApiErrorMessages.INTERNAL_SERVER_ERROR);
    }

    public CustomInternalServerException(String s) {
        super(s);
    }
}
