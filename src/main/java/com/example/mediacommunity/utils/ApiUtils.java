package com.example.mediacommunity.utils;

import com.example.mediacommunity.Exception.CustomRuntimeException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ApiUtils {
    public static <T> ApiResult<T> success(T response) {
        log.info("successful response payload: {}", response);
        return new ApiResult<>(true, response, null);
    }

    public static <T> ApiResult<T> error(CustomRuntimeException throwable) {
        ApiError apiError = new ApiError(throwable);
        log.info("error: {}", apiError);
        return new ApiResult<>(false, null, apiError);
    }

    public static <T> ApiResult<T> error(String code, String message) {
        ApiError apiError = new ApiError(code, message);
        log.info("error: {}", apiError);
        return new ApiResult<>(false, null, apiError);
    }
}
