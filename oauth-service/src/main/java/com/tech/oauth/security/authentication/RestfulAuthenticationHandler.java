package com.tech.oauth.security.authentication;

import com.tech.oauth.security.property.OauthSecurityProperties;
import com.tech.oauth.security.vo.OprResult;
import com.tech.oauth.service.util.JSONResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestfulAuthenticationHandler
    implements AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler {

    private static Logger logger = LoggerFactory.getLogger(RestfulAuthenticationHandler.class);
    @Autowired
    private OauthSecurityProperties oauthSecurityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        logger.debug("authentication successfully done");
        // JSONResponseUtil.writeJsonData(OprResult.success(), response);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {
        logger.debug("authentication failed");
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException {
        String responseType = request.getParameter("responseType");
        String redirectUrl = request.getParameter("redirectUrl");

        if ("redirect".equals(responseType)) {
            // after oauth service logout, redirect to the url specified by the client application
            response.setStatus(HttpStatus.FOUND.value());
            response.setHeader("location", redirectUrl);

        } else {
            if (StringUtils.isNotEmpty(oauthSecurityProperties.getLogoutUrl())) {
                // if a logout url is specified, indicate the client js to redirect to the url(for logout oauth service)
                Map<String, String> map = new HashMap<String, String>();
                map.put("logoutUrl", oauthSecurityProperties.getLogoutUrl());
                JSONResponseUtil.writeJsonData(OprResult.success(map), response);
            } else {
                JSONResponseUtil.writeJsonData(OprResult.success(), response);
            }
        }

    }
}
