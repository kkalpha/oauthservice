package com.tech.oauth.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * Resource (API) service configuration
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "rest_api";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {

        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    /*
     *  only requests matches /api/** need to go through resource authentication filter chain
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

        // @formatter:off
        http.anonymous().disable()
            .requestMatchers().mvcMatchers("/api/**")
            .and()
            .authorizeRequests().mvcMatchers("/api/**").authenticated()
            .and()
            .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
        // @formatter:on
    }

}