package com.tech.oauth.resource.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.oauth.entity.AuthClientDetails;
import com.tech.oauth.service.AuthClientDetailsService;

// http://localhost:8088/permitall/test?cilentId=cilentId
// http://localhost:8088/permitall/student?name=clientId
// http://localhost:8088/permitall/app?name=clientId
// http://localhost:8088/permitall/details?name=pbAjLKuogxziSF2hGlRdl066

@RestController
@RequestMapping("/permitall")
public class PermitAllController {
    private static final Logger logger = LoggerFactory.getLogger(PermitAllController.class);

    @Autowired
    private AuthClientDetailsService authClientDetailsService;

    @GetMapping("/details")
    public AuthClientDetails details(String name) {

        logger.debug(name);

        AuthClientDetails oauthClient = authClientDetailsService.selectClientDetailsByClientId(name);

        return oauthClient;
    }

}
