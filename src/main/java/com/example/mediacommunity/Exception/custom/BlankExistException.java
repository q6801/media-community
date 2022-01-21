package com.example.mediacommunity.Exception.custom;

import com.example.mediacommunity.Exception.CustomRuntimeException;
import com.example.mediacommunity.Exception.ExceptionEnum;

public class BlankExistException extends CustomRuntimeException {
    public BlankExistException(ExceptionEnum error) {
        super(error);
    }
}
