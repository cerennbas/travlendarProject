package com.travlendar.springtravlendar.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class TravlendarExceptionControllerAdvice extends ResponseEntityExceptionHandler{

    @ExceptionHandler(TravlendarException.class)
    public ResponseEntity exception(TravlendarException e) {
            return new ResponseEntity(e.getMessage(), e.getHttpStatus());
    }
}
