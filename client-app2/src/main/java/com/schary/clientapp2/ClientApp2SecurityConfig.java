package com.schary.clientapp2;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableOAuth2Sso
@Configuration
public class ClientApp2SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Override
    public void configure(HttpSecurity http) throws Exception {
        System.out.println("in configure client");
        http.authorizeRequests()
            .antMatchers("/", "/login","/index","/securedPage")
            .permitAll()
            .anyRequest()
            .authenticated();

    }

}
