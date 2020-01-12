 package com.tech.oauth.service;

import com.tech.oauth.entity.AuthClientDetailsExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import com.tech.oauth.mapper.AuthClientDetailsMapper;
import com.tech.oauth.entity.AuthClientDetails;

import java.util.Arrays;
import java.util.HashSet;

 @Service
public class AuthClientDetailsService {
     @Autowired
     private AuthClientDetailsMapper authClientDetailsMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

     public AuthClientDetails selectClientDetailsByClientId(String clientId) {
         return authClientDetailsMapper.selectByClientId(clientId);
     }

    public ClientDetails getClientDetails(String clientId) {
        AuthClientDetails authClientDetails = authClientDetailsMapper.selectByClientId(clientId);

        String clientSecretAfterEncoder = passwordEncoder.encode(authClientDetails.getClientSecret());

        BaseClientDetails clientDetails = new BaseClientDetails();

        clientDetails.setClientId(authClientDetails.getClientId());
        clientDetails.setClientSecret(clientSecretAfterEncoder);

        clientDetails.setRegisteredRedirectUri(new HashSet<>(Arrays.asList(authClientDetails.getRedirectUri().split(","))));

        clientDetails.setAuthorizedGrantTypes(Arrays.asList(authClientDetails.getGrantType().split(",")));
        clientDetails.setScope(Arrays.asList(authClientDetails.getScope().split(",")));

        clientDetails.setAutoApproveScopes(Arrays.asList(authClientDetails.getScope().split(",")));

        clientDetails.setAccessTokenValiditySeconds(60*60);

        clientDetails.setRefreshTokenValiditySeconds(24*3600);


        return clientDetails;

    }


}
