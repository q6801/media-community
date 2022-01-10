package com.example.mediacommunity.Exception.custom;

import com.example.mediacommunity.Exception.CustomRuntimeException;
import com.example.mediacommunity.Exception.ExceptionEnum;
import lombok.Getter;

@Getter
public class UserNotExistException extends CustomRuntimeException {
    public UserNotExistException(ExceptionEnum error) {
        super(error);
    }
}
