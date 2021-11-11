package com.example.mediacommunity;

import com.example.mediacommunity.interceptor.LoginCheckInterceptor;
import com.example.mediacommunity.interceptor.LogoutCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogoutCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login", "/css/**", "/*.ico", "/error"
                );
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/login", "/signup");
    }
}