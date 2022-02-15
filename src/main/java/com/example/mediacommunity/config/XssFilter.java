package com.example.mediacommunity.config;

import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class XssFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println(request.getContentType());

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())) {
            System.out.println(MediaType.APPLICATION_JSON_VALUE);
            HttpServletRequest requestWrapper = new RequestWrapper(httpRequest);
            chain.doFilter(requestWrapper, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
