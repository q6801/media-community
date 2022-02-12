package com.example.mediacommunity.Exception.custom;

import com.example.mediacommunity.Exception.CustomRuntimeException;
import com.example.mediacommunity.Exception.ExceptionEnum;

public class NotFoundPageException extends CustomRuntimeException {
    public NotFoundPageException(ExceptionEnum error) {
        super(error);
    }
}
