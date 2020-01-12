package com.tech.oauth.security.authentication.impl;

import com.tech.oauth.security.authentication.BaseAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(998)
public class AppAuthenticationProvider extends BaseAuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(AppAuthenticationProvider.class);

    @Override
    protected boolean authenticate(String userName, String password) {
        if ("yk@xxx.com".equals(userName)) {
            return true;
        }
        return false;
    }


}
