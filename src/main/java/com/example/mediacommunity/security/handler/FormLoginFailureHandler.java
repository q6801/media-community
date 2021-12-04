package com.example.mediacommunity.security.handler;

import com.example.mediacommunity.security.BadProviderException;
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
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Boolean idFail = false;
        Boolean pwFail = false;
        Boolean providerFail = false;

        if (exception instanceof BadProviderException) {
            providerFail = true;
        } else if (exception instanceof InternalAuthenticationServiceException) {
            idFail = true;
        } else if (exception instanceof BadCredentialsException) {
            pwFail = true;
        }

        request.setAttribute("idFail", idFail);
        request.setAttribute("pwFail", pwFail);
        request.setAttribute("providerFail", providerFail);
        request.getRequestDispatcher("/loginFail").forward(request, response);
    }
}
