package com.example.mediacommunity.config;

import com.example.mediacommunity.interceptor.LoginCheckInterceptor;
import com.example.mediacommunity.interceptor.LogoutCheckInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LogoutCheckInterceptor())
//                .order(1)
//                .addPathPatterns("/**")
//                .excludePathPatterns(
//                        "/", "/login/**", "/signup/**", "/css/**", "/*.ico", "/error",
//                        "/boards/**"
//                );
//
//        registry.addInterceptor(new LoginCheckInterceptor())
//                .order(2)
//                .addPathPatterns("/login", "/signup");
//
//        registry.addInterceptor(new LogoutCheckInterceptor())
//                .order(3)
//                .addPathPatterns("/boards/add", "/boards/edit/**");
//    }
}
