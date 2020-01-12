package com.tech.oauth.security.provider;

import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import com.tech.oauth.exception.OauthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.tech.OAuthServerApplication;
import com.tech.oauth.entity.AuthClientDetails;
import com.tech.oauth.service.AuthClientDetailsService;

public class AppClientDetailsService implements ClientDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(OAuthServerApplication.class);

    @Autowired
    private AuthClientDetailsService authClientDetailsService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    private LoadingCache<String, ClientDetails> authClientDetailsCache;
    
    public AppClientDetailsService() {
        authClientDetailsCache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.HOURS)
            .build(new CacheLoader<String, ClientDetails>() {
                @Override
                public ClientDetails load(String clientId) {
                    return authClientDetailsService.getClientDetails(clientId);
                }
            });
    }
    
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        logger.debug("load client information by clientId: {}",clientId);

        try {
            return authClientDetailsCache.get(clientId);
        } catch (Exception e) {
            logger.error("client details not found {}",e);
            throw new OauthException("client details not found,check table auth_client_details",e);
        }

    }


}
