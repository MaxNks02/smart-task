package com.example.smartbank_task_webflux.exception;

import com.example.smartbank_task_webflux.exception.handler.ApiErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ApiErrorMessages.NOT_FOUND)
public class CustomNotFoundException extends RuntimeException {

    public CustomNotFoundException() {
        this(ApiErrorMessages.NOT_FOUND);
    }

    public CustomNotFoundException(String s) {
        super(s);
    }

}
