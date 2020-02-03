package com.tech.oauthclient.controller;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tech.oauthclient.model.AuthorizationResponse;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/callback")
public class OAuthCallbackController {
    private static final Logger logger = LoggerFactory.getLogger(OAuthCallbackController.class);

    @Value("${spring.security.oauth2.client.registration.my-client-1.client-id}")
    private String clientId;
    
    @Value("${spring.security.oauth2.client.registration.my-client-1.client-secret}")
    private String clientSecret;
    
    @Value("${spring.security.oauth2.client.registration.my-client-1.redirect-uri}")
    private String redirectUri;
    
    @Value("${spring.security.oauth2.client.provider.my-oauth-provider.authorization-uri}")
    private String authorizationUri;
    
    @Value("${spring.security.oauth2.client.provider.my-oauth-provider.token-uri}")
    private String tokenUri;
    
    @Value("${spring.security.oauth2.client.provider.my-oauth-provider.user-info-uri}")
    private String userInfoUri;
    
    
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String callback(HttpServletRequest request) {
        String codeString = request.getParameter("code");
        logger.debug(codeString);

        if (!StringUtils.isEmpty(codeString)) {

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("code", codeString);
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("redirect_uri", redirectUri);

            AuthorizationResponse response
                = restTemplate.postForObject(tokenUri, params, AuthorizationResponse.class);
            logger.debug("oauth/token {}",response);

            //demo for parse jwt token content
            parseJWT(response.getAccess_token());

            String resourceParams = "?access_token=" + response.getAccess_token() +
                "&userId=297ec9ff69f6b82a0169f6b83a8c0001";

            String accessTokeInfo = response.toString();
            logger.debug("oauth/accessTokeInfo {}",accessTokeInfo);

            String responseString
                = restTemplate.getForObject(userInfoUri +resourceParams, String.class);
            logger.debug(responseString);
            
            //TODO,这里Demo只是显示用户信息到页面，真实应用应该是，做完认证跳转到用户认证前访问的页面
            
            return responseString + " --- " + accessTokeInfo;
            
        }
        return "Should not come here";
    }
    private void parseJWT(String jwtToken){

        try {
            Claims claims  = Jwts.parser().setSigningKey("todo".getBytes("UTF-8"))
                        .parseClaimsJws(jwtToken).getBody();
            claims.keySet().forEach( key ->{
                logger.debug("jwt key {}",key);
                logger.debug("jwt value {}", claims.get(key));
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("jwt parse error",e);
        }

    }
}
