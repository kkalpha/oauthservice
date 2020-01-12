package com.tech.oauth.security.authentication.impl;

import com.tech.oauth.security.authentication.BaseAuthenticationProvider;
import com.tech.oauth.security.property.OauthSecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
@Order(997)
public class LocalConfigAuthenticationProvider extends BaseAuthenticationProvider {
    private static Logger LOG = LoggerFactory.getLogger(LocalConfigAuthenticationProvider.class);
    @Autowired
    private OauthSecurityProperties oauthSecurityProperties;


    @Override
    protected boolean authenticate(String loginAccount, String password) {
        try (BufferedReader bufferedReader
            = new BufferedReader(new FileReader(ResourceUtils.getFile(oauthSecurityProperties.getWhiteUserFile())))) {
            String credential = null;
            while ((credential = bufferedReader.readLine()) != null) {
                if (verify(credential, loginAccount, password)) {

                    return true;
                }
            }

            return false;

        } catch (Exception e) {
            LOG.debug("Does the whitelist file exist: {}", oauthSecurityProperties.getWhiteUserFile());
            return false;
        }

    }

    private boolean verify(String credential, String loginAccount, String password) {
        String[] userNameAndPassword = credential.split("/");
        switch (userNameAndPassword.length) {
            case 1:
                return StringUtils.equalsIgnoreCase(loginAccount, userNameAndPassword[0]);
            case 2:
                return StringUtils.equalsIgnoreCase(loginAccount, userNameAndPassword[0])
                    && StringUtils.equals(password, userNameAndPassword[1]);
            default:
                return false;
        }
    }
}
