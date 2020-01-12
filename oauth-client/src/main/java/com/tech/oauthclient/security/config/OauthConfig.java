package com.tech.oauthclient.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@EnableOAuth2Sso
@Configuration
public class OauthConfig extends WebSecurityConfigurerAdapter{


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
        .authorizeRequests()
            .antMatchers("/", "/callback**","/favicon.ico").permitAll()
            .anyRequest().authenticated()
            .and()
        .oauth2Login();

    }
}
