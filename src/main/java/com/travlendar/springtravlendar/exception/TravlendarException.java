package com.travlendar.springtravlendar.exception;

import org.springframework.http.HttpStatus;

public class TravlendarException extends Exception {
    private HttpStatus httpStatus;

    public TravlendarException() {
        setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public TravlendarException(String message) {
        super(message);
        setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public TravlendarException(String message, HttpStatus httpStatus) {
        super(message);
        setHttpStatus(httpStatus);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
