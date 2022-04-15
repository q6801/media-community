package com.example.mediacommunity.utils;

import com.example.mediacommunity.Exception.CustomRuntimeException;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiError {
    private String errorCode;
    private String errorMessage;

    public ApiError(CustomRuntimeException ex) {
        this.errorCode = ex.getError().getCode();
        this.errorMessage = ex.getError().getMessage();
    }

    public ApiError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
