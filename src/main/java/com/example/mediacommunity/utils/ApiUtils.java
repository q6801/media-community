package com.example.mediacommunity.utils;

import com.example.mediacommunity.Exception.CustomRuntimeException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ApiUtils {
    public static <T> ApiResult<T> success(T response) {
        log.info("fuck");
        return new ApiResult<>(true, response, null);
    }

    public static <T> ApiResult<T> error(CustomRuntimeException throwable) {
        return new ApiResult<>(false, null, new ApiError(throwable));
    }

    public static <T> ApiResult<T> error(String code, String message) {
        return new ApiResult<>(false, null, new ApiError(code, message));
    }
}
