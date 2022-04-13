package com.example.mediacommunity.Exception;

import com.example.mediacommunity.utils.ApiResult;
import com.example.mediacommunity.utils.ApiUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler({CustomRuntimeException.class})
    public ApiResult<?> exceptionHandler(CustomRuntimeException ex) {
        ApiResult<?> error = ApiUtils.error(ex);
        return error;
    }
}
