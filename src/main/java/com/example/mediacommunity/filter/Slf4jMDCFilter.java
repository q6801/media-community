package com.example.mediacommunity.filter;

import com.amazonaws.util.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class Slf4jMDCFilter extends OncePerRequestFilter {
    private final String responseHeader = "Response_Token";
    private final String mdcTokenKey = "Slf4jMDCFilter.UUID";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final String token = UUID.randomUUID().toString().toUpperCase().replace("-", "");
            MDC.put(mdcTokenKey, token);
            response.addHeader(responseHeader, token);
            filterChain.doFilter(request, response);

        } finally {
            MDC.remove(mdcTokenKey);
        }
    }
}
