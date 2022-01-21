package com.example.mediacommunity.Exception.custom;

import com.example.mediacommunity.Exception.CustomRuntimeException;
import com.example.mediacommunity.Exception.ExceptionEnum;

public class BadValueException extends CustomRuntimeException {
    public BadValueException(ExceptionEnum error) {
        super(error);
    }
}
