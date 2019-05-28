package com.leisure_pass.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(Arrays.asList(ex.getMessage())));
    }

    @ExceptionHandler(value = {CustomerNotFoundException.class, PassNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(RuntimeException ex) {
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(Arrays.asList(ex.getMessage())));
    }

}
