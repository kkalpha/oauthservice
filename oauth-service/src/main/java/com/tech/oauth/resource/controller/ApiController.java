package com.tech.oauth.resource.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.oauth.resource.model.SimpleUser;

@RestController
@RequestMapping("/api")
public class ApiController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    @GetMapping("/user/simple")
    public SimpleUser user() {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
               
        logger.debug("Authentication: {}",auth);
  
        if(auth != null) {
            //TODO
            Object principal = auth.getPrincipal();
            logger.debug("Principal: {}",auth.getPrincipal());
            if(User.class.isAssignableFrom(principal.getClass())) {
                User user = (User) principal;
                return new SimpleUser(user.getUsername());
            }else {
                return new SimpleUser(auth.getPrincipal().toString());
            }
            
            
           
        }
        return SimpleUser.unKnonwnUser();
    }

}
