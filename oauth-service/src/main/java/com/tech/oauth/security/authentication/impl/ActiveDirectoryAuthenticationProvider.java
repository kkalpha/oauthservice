package com.tech.oauth.security.authentication.impl;

import com.tech.oauth.security.authentication.BaseAuthenticationProvider;
import com.tech.oauth.security.property.OauthSecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;


@Component
@Order(999)
public class ActiveDirectoryAuthenticationProvider extends BaseAuthenticationProvider {
    private static Logger LOG = LoggerFactory.getLogger(ActiveDirectoryAuthenticationProvider.class);

    @Autowired
    private OauthSecurityProperties oauthSecurityProperties;

    public void setSysSecurityProperties(OauthSecurityProperties oauthSecurityProperties) {
        this.oauthSecurityProperties = oauthSecurityProperties;
    }

    @Override
    protected boolean authenticate(String loginAccount, String password) {
        try {
            loginAccount = loginAccount.toLowerCase();
            return login(oauthSecurityProperties.getLdapUrl(), loginAccount, password);
        } catch (NamingException e) {
            LOG.error("AD verified failed:{}", loginAccount);
            return false;
        }
    }

    private boolean login(String ldapUrl, String userName, String password) throws NamingException {

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return false;
        }

        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

        env.put(Context.PROVIDER_URL, ldapUrl);

        env.put(Context.SECURITY_PRINCIPAL, userName);
        env.put(Context.SECURITY_CREDENTIALS, password);
        // intial success means user/pwd is valid
        new InitialDirContext(env);
        return true;

    }
}
