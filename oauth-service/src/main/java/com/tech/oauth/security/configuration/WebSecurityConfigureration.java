package com.tech.oauth.security.configuration;

import com.tech.oauth.security.authentication.RestfulAuthenticationHandler;
import org.apache.commons.collections.CollectionUtils;
import org.checkerframework.checker.formatter.qual.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigureration extends WebSecurityConfigurerAdapter {

    @Autowired
    protected List<AuthenticationProvider> authenticationProviders;

    @Autowired
    protected RestfulAuthenticationHandler restfulAuthenticationHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        if (CollectionUtils.isNotEmpty(authenticationProviders)) {
            authenticationProviders.forEach(provider -> auth.authenticationProvider(provider));
        }
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/static/**", "/favicon.ico", "/permitall/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().mvcMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login");

    }

}
