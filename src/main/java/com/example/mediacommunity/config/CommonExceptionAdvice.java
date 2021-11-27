package com.example.mediacommunity.config;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionAdvice {
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public String except(Exception e, BindingResult bindingResult) {
//        System.out.println("username not found exception");
//        bindingResult.reject("idFail");
//        return "/login/loginForm";
//    }
}
