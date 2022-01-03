package com.example.mediacommunity.config;

import com.example.mediacommunity.security.service.CustomOAuth2UserService;
import com.example.mediacommunity.security.service.CustomUserDetailsService;
import com.example.mediacommunity.security.handler.FormLoginFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService userService;
    private final FormLoginFailureHandler failureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin().disable().logout().disable();
        http
            .authorizeRequests()
                .antMatchers("/login", "/signup", "/", "/boards").permitAll()
                .regexMatchers("^/boards\\/[0-9]+.\\w+.\\w+").permitAll()
//                .antMatchers("/**").authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .usernameParameter("loginId")
                .failureHandler(failureHandler)
                .defaultSuccessUrl("/")
                .and()
            .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                    .userService(customOAuth2UserService)
                    .and()
                .and()
            .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/")
            .and().csrf().disable()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
}
