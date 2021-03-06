package com.example.mediacommunity.security.handler;

import com.example.mediacommunity.Exception.ExceptionEnum;
import com.example.mediacommunity.security.BadProviderException;
import com.example.mediacommunity.utils.ApiResult;
import com.example.mediacommunity.utils.ApiUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FormLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof BadProviderException) {
            setResponse(response, ExceptionEnum.BAD_PROVIDER);
        } else if (exception instanceof InternalAuthenticationServiceException) {
            setResponse(response, ExceptionEnum.USER_NOT_EXIST);
        } else if (exception instanceof BadCredentialsException) {
            setResponse(response, ExceptionEnum.BAD_PASSWORD);
        }
    }

    private void setResponse(HttpServletResponse response, ExceptionEnum exceptionEnum) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        ApiResult<Object> error = ApiUtils.error(exceptionEnum.getCode(), exceptionEnum.getMessage());

        ObjectMapper objectMapper = new ObjectMapper();
        String responseJson = objectMapper.writeValueAsString(error);

        response.getWriter().print(responseJson);
    }
}
