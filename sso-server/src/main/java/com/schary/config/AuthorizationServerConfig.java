package com.schary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
  TokenStore tokenStore;

  @Autowired
	private DefaultTokenServices tokenServices;

	@Autowired
    private AuthenticationManager authenticationManagerBean;

  @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception
	{
        oauthServer
            .tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()")
            .allowFormAuthenticationForClients();  
}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		/*clients.inMemory().withClient("sampleClientId").secret(passwordEncoder.encode("secret"))
				.authorizedGrantTypes("password", "authorization_code", "refresh_token")
				.authorities("READ_ONLY_CLIENT")
        .scopes("read_profile_info","read","write")
 				.autoApprove(true);*/

		clients
			.inMemory()
			.withClient("sampleClientId")
			.secret(passwordEncoder.encode("secret"))
			.authorizedGrantTypes("password","client_credentials")
			.authorities("ROLE_CLIENT")
			.scopes("read", "write");
			//.resourceIds("oauth2-resource")
			//.redirectUris("http://localhost:8082/ui/secure")
			//.accessTokenValiditySeconds(50000)
			//.refreshTokenValiditySeconds(50000);		 

	}
		@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
     endpoints.tokenStore(tokenStore)
		          .tokenServices(tokenServices)
							.authenticationManager(authenticationManagerBean);  
   }
}