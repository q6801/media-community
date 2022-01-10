package com.example.mediacommunity.Exception;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    USER_INFO_NOT_FOUND("auth-0001", "이 요청은 인증이 필요하다."),
    USER_NOT_EXIST("auth-0002", "해당되는 id 없음"),
    BAD_PASSWORD("auth-0003", "잘못된 비밀번호"),
    USER_ALREADY_EXIST("auth-004", "이미 존재하는 id이다."),
    BAD_PROVIDER("auth-005", "잘못된 provider이다."),

    ;
    

    private String code;
    private String message;

    ExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
