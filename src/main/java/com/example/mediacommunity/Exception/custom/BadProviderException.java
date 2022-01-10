package com.example.mediacommunity.Exception.custom;

import com.example.mediacommunity.Exception.CustomRuntimeException;
import com.example.mediacommunity.Exception.ExceptionEnum;

public class BadProviderException extends CustomRuntimeException {
    public BadProviderException(ExceptionEnum error) {
        super(error);
    }
}
