package com.springboot.jasperreports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import com.springboot.jasperreports.filter.AccessFilter;

import java.util.Collections;
/**
 * Main implementation class which serves two purpose in a spring boot application: Configuration and bootstrapping.
 * @author yatin-batra
 */
@SpringBootApplication
public class Jasperreports {
	 @Value("${services.auth}")
    private String authService;

    @Bean
    public FilterRegistrationBean jasperFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new AccessFilter());
        registrationBean.setInitParameters(Collections.singletonMap("services.auth", authService));
        registrationBean.addUrlPatterns("/");

        return registrationBean;
    }

	public static void main(String[] args) {
		SpringApplication.run(Jasperreports.class, args);
	}
}
