package com.schary.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import com.schary.util.HttpClientUtil;
import com.schary.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;


import java.io.IOException;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {
 
   private static final String referrerCookieName = "REFERRER";

   private String referrer;

   @Autowired
   private HttpClientUtil httpClientUtil;

    private String redirect_uri;
    public void setRedirectURI(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getRedirectURI() {
       return redirect_uri;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //do some logic here if you want something to be done whenever
        //the user successfully logs in.
 
        HttpSession session = httpServletRequest.getSession();
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("username", authUser.getUsername());
        session.setAttribute("authorities", authentication.getAuthorities());
 
        //set our response to OK status
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        httpClientUtil.generateToken();

        
         //String redirect_uri = CookieUtil.getValue(httpServletRequest,"redirect_uri");
        //session.setAttribute("referrer",getReferrer());



        
        // add request headers
        //request.addHeader("Authorization", "mkyong");
        //request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

        
        //setReferrer(httpServletRequest.getHeader("Referer"));        
 
        //since we have created our custom success handler, its up to us to where
        //we will redirect the user after successfully login
        httpServletResponse.sendRedirect(getRedirectURI()+"?user="+authUser.getUsername());
    }



    
}