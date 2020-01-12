package com.tech.oauth.security.authentication;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public abstract class BaseAuthenticationProvider implements AuthenticationProvider {
    private static Logger logger = LoggerFactory.getLogger(BaseAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        String originalName = authentication.getName().toLowerCase();
        if (StringUtils.countMatches(authentication.getName(), '@') > 1) {
            originalName = originalName.substring(0, originalName.lastIndexOf("@"));
        }
        String name = StringUtils.trim(originalName);
        String password = authentication.getCredentials().toString();

        if (authenticate(name, password)) {
            Collection<GrantedAuthority> grantedAuthorities
                = AuthorityUtils.commaSeparatedStringToAuthorityList("sso_client");
            UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(name, password, grantedAuthorities);

            return token;
        } else {
            return null;
            // throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authenticationToken) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationToken);
    }

    protected abstract boolean authenticate(String userName, String password);
}
