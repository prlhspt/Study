package com.prlhspt.market.config;

import com.prlhspt.market.jwt.TokenProvider;
import com.prlhspt.market.jwt.filter.ExceptionHandlerFilter;
import com.prlhspt.market.jwt.filter.JwtFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private TokenProvider tokenProvider;
    private ExceptionHandlerFilter exceptionHandlerFilter;

    public JwtSecurityConfig(TokenProvider tokenProvider, ExceptionHandlerFilter exceptionHandlerFilter) {
        this.tokenProvider = tokenProvider;
        this.exceptionHandlerFilter = exceptionHandlerFilter;
    }

    @Override
    public void configure(HttpSecurity http) {
        JwtFilter customFilter = new JwtFilter(tokenProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlerFilter, JwtFilter.class);

    }
}
