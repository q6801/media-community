package com.example.mediacommunity.Exception.custom;

import com.example.mediacommunity.Exception.CustomRuntimeException;
import com.example.mediacommunity.Exception.ExceptionEnum;
import lombok.Getter;

@Getter
public class UserInfoNotFoundException extends CustomRuntimeException {
    public UserInfoNotFoundException(ExceptionEnum error) {
        super(error);
    }
}
