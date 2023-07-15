package com.banao.task1;

import com.banao.task1.filters.AuthFilter;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@SpringBootApplication
public class Task1Application {

	public static void main(String[] args) {
		SpringApplication.run(Task1Application.class, args);
	}
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter () {
		FilterRegistrationBean<CorsFilter> corsFilterBean = new FilterRegistrationBean<>();
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.addAllowedHeader("*");
		source.registerCorsConfiguration("/**", configuration);
		corsFilterBean.setFilter(new CorsFilter());
		corsFilterBean.setOrder(0);

		return corsFilterBean;
	}
	@Bean
	public FilterRegistrationBean<AuthFilter> filterFilterRegistrationBean () {
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		AuthFilter authFilter = new AuthFilter();
		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns("/api");

		return registrationBean;
	}

}
