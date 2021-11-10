package com.example.mediacommunity.interceptor;

import com.example.mediacommunity.constant.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
