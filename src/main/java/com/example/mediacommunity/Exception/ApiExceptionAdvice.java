package com.example.mediacommunity.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler({CustomRuntimeException.class})
    public ResponseEntity<ApiErrorResponse> exceptionHandler(CustomRuntimeException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getError().getCode(), ex.getError().getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
