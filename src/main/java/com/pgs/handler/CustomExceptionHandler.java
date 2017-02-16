package com.pgs.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by mmalek on 2/16/2017.
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ HttpClientErrorException.class })
    public ResponseEntity<Object> handleAccessDeniedException(HttpClientErrorException e, WebRequest request) {
        HttpStatus statusCode = e.getStatusCode();
        String body = e.getResponseBodyAsString();
        return new ResponseEntity<Object>(body, statusCode);
    }
}