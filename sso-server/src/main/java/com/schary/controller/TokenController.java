package com.schary.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.schary.util.CookieUtil;

@Controller
public class TokenController {

  
    @Autowired
    private DefaultTokenServices tokenServices;

    @Autowired
    private TokenStore tokenStore;

    private static final String accessTokenCookieName = "ACCESS-TOKEN";


    @RequestMapping(method = RequestMethod.POST, value = "/oauth/token/revokeById/{tokenId}")
    @ResponseBody
    public void revokeToken(HttpServletRequest request, @PathVariable String tokenId) {
        tokenServices.revokeToken(tokenId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tokens/{client}")
    @ResponseBody
    public String getTokens(HttpServletResponse httpServletResponse, @PathVariable String client) {
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId(client);
        List<String> tokenList = Optional.ofNullable(tokens).orElse(Collections.emptyList()).stream().map(OAuth2AccessToken::getValue).collect(Collectors.toList());
        CookieUtil.create(httpServletResponse, accessTokenCookieName, tokenList.get(0), false, -1, "localhost");
        return tokenList.get(0);
   
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tokens/revokeRefreshToken/{tokenId:.*}")
    @ResponseBody
    public String revokeRefreshToken(@PathVariable String tokenId) {
        if (tokenStore instanceof JdbcTokenStore) {
            ((JdbcTokenStore) tokenStore).removeRefreshToken(tokenId);
        }
        return tokenId;
    }



}