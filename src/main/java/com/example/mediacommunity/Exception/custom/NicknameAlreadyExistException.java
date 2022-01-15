package com.example.mediacommunity.Exception.custom;

import com.example.mediacommunity.Exception.CustomRuntimeException;
import com.example.mediacommunity.Exception.ExceptionEnum;

public class NicknameAlreadyExistException extends CustomRuntimeException {
    public NicknameAlreadyExistException(ExceptionEnum error) {
        super(error);
    }
}
