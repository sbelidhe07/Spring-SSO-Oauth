package com.schary.clientapp1;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.beans.factory.annotation.Value;
import com.schary.clientapp1.filter.AccessFilter;

@SpringBootApplication
public class ClientApp1Application extends SpringBootServletInitializer
{

	@Value("${services.auth}")
  private String authService;
	

    @Bean
    public FilterRegistrationBean AccessFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new AccessFilter());
        registrationBean.setInitParameters(Collections.singletonMap("services.auth", authService));
        registrationBean.addUrlPatterns("/");

        return registrationBean;
    }

	public static void main(String[] args) {
		SpringApplication.run(ClientApp1Application.class, args);
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

}
