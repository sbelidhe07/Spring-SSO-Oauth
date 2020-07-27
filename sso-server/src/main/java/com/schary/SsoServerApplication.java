package com.schary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import com.schary.util.HttpClientUtil;
import com.schary.handler.CustomSuccessHandler;

@SpringBootApplication(scanBasePackages = "com.schary")
@EnableAuthorizationServer
@EnableResourceServer
public class SsoServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SsoServerApplication.class, args);
    }


	@Bean
   public TokenStore tokenStore() {
     
		 InMemoryTokenStore tokenStore = new InMemoryTokenStore();
		 tokenStore.setFlushInterval(1);

		 return tokenStore;
  }

	 @Bean
    public DefaultTokenServices tokenServices() throws Exception {
			  DefaultTokenServices tokenServices = new DefaultTokenServices(); 
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setAccessTokenValiditySeconds(-1);
				return tokenServices;
		}

		@Bean
		public HttpClientUtil httpClientUtil() {
			return new HttpClientUtil();
		}

		@Bean
  public CustomSuccessHandler successHandler() {
    return new CustomSuccessHandler();
  }  

}