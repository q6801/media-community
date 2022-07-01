package com.example.mediacommunity.Exception;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    USER_INFO_NOT_FOUND("auth-001", "이 요청은 인증이 필요하다."),
    USER_NOT_EXIST("auth-002", "해당되는 id 없음"),
    BAD_PASSWORD("auth-003", "잘못된 비밀번호"),
    USER_ALREADY_EXIST("auth-004", "이미 존재하는 id이다."),
    BAD_PROVIDER("auth-005", "잘못된 provider이다."),
    NICKNAME_ALREADY_EXIST("auth-006", "이미 존재하는 nickname이다."),
    NOT_ALLOWED_ACCESS("auth-007", "잘못된 접근이다. (권한 없음)"),
    PASSWORD_MISMATCH("auth-008", "비밀번호와 비밀번호 재확인이 일치하지 않는다."),

    BLANK_EXIST("common-001", "값을 다 넣어주세요"),
    BAD_VALUE("common-002", "잘못된 값입니다."),
    NOT_FOUND_PAGE("common-003", "잘못된 페이지입니다.")
    ;

    private String code;
    private String message;

    ExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
