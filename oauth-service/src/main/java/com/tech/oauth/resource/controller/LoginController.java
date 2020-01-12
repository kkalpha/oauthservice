package com.tech.oauth.resource.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
  
    @RequestMapping("/login")
    public String login(HttpServletRequest request,Model model) {
   
            HttpSession session = request.getSession(false);
            
            model.addAttribute("loginResult","");
            model.addAttribute("className","login-normal");
            
            if (session != null) {
                AuthenticationException ex = (AuthenticationException) session
                        .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                logger.debug("AuthenticationException {} ", ex);
                if(ex != null) {
                    model.addAttribute("loginResult","Invalid credentials");
                    model.addAttribute("className","login-error");
                }
            }
            
        return "login";
    }
    

}
