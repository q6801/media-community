package com.example.mediacommunity.Exception.custom;

import com.example.mediacommunity.Exception.CustomRuntimeException;
import com.example.mediacommunity.Exception.ExceptionEnum;

public class UserAlreadyExistException extends CustomRuntimeException {
    public UserAlreadyExistException(ExceptionEnum error) {
        super(error);
    }
}
