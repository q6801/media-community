package com.example.mediacommunity.interceptor;

import com.example.mediacommunity.constant.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 로그인한 사용자가 접근이 불가능 곳에 접근할 때
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute(SessionConst.LOGIN_MEMBER) != null) {
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
