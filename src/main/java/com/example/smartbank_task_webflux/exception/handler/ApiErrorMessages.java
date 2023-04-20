package com.example.smartbank_task_webflux.exception.handler;


public class ApiErrorMessages {

    private ApiErrorMessages() {
        throw new IllegalStateException("ApiErrorMessages class");
    }

    public static final String BAD_REQUEST = "Invalid request. Please check request once more. ";
    public static final String NOT_FOUND = "Could not find requesting data. ";
    public static final String INTERNAL_SERVER_ERROR = "Error occurs while exchange data with database. Please try again later, after checking given error details. ";


}
