package com.example.mediacommunity.Exception;

import com.example.mediacommunity.utils.ApiResult;
import com.example.mediacommunity.utils.ApiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler({CustomRuntimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult exceptionHandler(CustomRuntimeException ex) {
        return ApiUtils.error(ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError: bindingResult.getFieldErrors()) {
//            sb.append("[").append(fieldError.getField()).append("]은(는) ");
            sb.append(fieldError.getDefaultMessage());
            sb.append("\n");
        }
        return ApiUtils.error("common-002", sb.toString());
    }
}
