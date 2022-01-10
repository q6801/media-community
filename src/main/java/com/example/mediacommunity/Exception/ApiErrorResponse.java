package com.example.mediacommunity.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiErrorResponse {
    private String errorCode;
    private String errorMessage;
}
