package com.example.mediacommunity.config;

import com.example.mediacommunity.service.member.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final LoginFailureHandler failureHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin().disable().logout().disable();
        http
            .authorizeRequests()
                .antMatchers("/login", "/signup", "/", "/boards", "/boards/{^[0-9]+$}").permitAll()
                .antMatchers("/boards/**").authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .usernameParameter("loginId")
                .failureHandler(failureHandler)
                .and()
            .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}
