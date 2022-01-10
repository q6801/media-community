package com.example.mediacommunity.Exception;

import lombok.Getter;

@Getter
public class CustomRuntimeException extends RuntimeException {
    private ExceptionEnum error;

    public CustomRuntimeException(ExceptionEnum error) {
        super(error.getMessage());
        this.error = error;
    }
}
