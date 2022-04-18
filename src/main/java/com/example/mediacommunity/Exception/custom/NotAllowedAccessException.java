package com.example.mediacommunity.Exception.custom;

import com.example.mediacommunity.Exception.CustomRuntimeException;
import com.example.mediacommunity.Exception.ExceptionEnum;

public class NotAllowedAccessException extends CustomRuntimeException {
    public NotAllowedAccessException(ExceptionEnum error) {
        super(error);
    }
}
