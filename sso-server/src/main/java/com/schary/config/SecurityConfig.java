package com.schary.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import com.schary.oauth2request.CustomTokenResponseConverter;
import com.schary.handler.CustomSuccessHandler;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

 @Autowired
 private CustomSuccessHandler  successHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

	
      http.csrf().disable();

      http
          .authorizeRequests()
          .antMatchers("/**", "/login", "/oauth/authorize").permitAll()          
          .anyRequest().authenticated()
          .and()
          .formLogin()
          .loginPage("/login")
          .successHandler(successHandler)         
          .permitAll();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user")
				.password(passwordEncoder().encode("user")).authorities("ADMIN");
	}

     @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}