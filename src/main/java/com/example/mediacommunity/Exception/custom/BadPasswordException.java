package com.example.mediacommunity.Exception.custom;

import com.example.mediacommunity.Exception.CustomRuntimeException;
import com.example.mediacommunity.Exception.ExceptionEnum;

public class BadPasswordException extends CustomRuntimeException {
    public BadPasswordException(ExceptionEnum error) {
        super(error);
    }
}
