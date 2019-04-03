package ar.com.tandilweb.byo.backend;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import ar.com.tandilweb.byo.backend.Filters.CorsFilter;
import ar.com.tandilweb.byo.backend.Filters.JWT.JWTFilter;
import ar.com.tandilweb.byo.backend.Filters.JWT.JWTValidFilter;
import ar.com.tandilweb.byo.backend.Model.JDBConfig;
import ar.com.tandilweb.byo.backend.utils.Mailer;

@SpringBootApplication
@ComponentScan({ "ar.com.tandilweb.byo.backend.Presentation.GQLServices",
		"ar.com.tandilweb.byo.backend.Presentation.RestServices",
		"ar.com.tandilweb.byo.backend.Presentation.WSockets.Services"})
@Import({ TransportFactory.class, GatewayFactory.class, JDBConfig.class, WebSocketConfig.class })
public class App extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public FilterRegistrationBean<Filter> registerCorsFilter() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(corsFilter());
		registration.addUrlPatterns("/graphql/*");
		// registration.addInitParameter("paramName", "paramValue");
		registration.setName("corsFilter");
		registration.setOrder(1);
		return registration;
	}

	public Filter corsFilter() {
		return new CorsFilter();
	}

	@Bean
	public FilterRegistrationBean<Filter> jwtFilterRegistration() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(getJwtFilter());
		registration.addUrlPatterns("/graphql/*");
		// registration.addInitParameter("paramName", "paramValue");
		registration.setName("jwtFilter");
		registration.setOrder(1);
		return registration;
	}

	@Bean
	public FilterRegistrationBean<Filter> jwtValidFilterRegistration() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(getJwtValidFilter());
		registration.addUrlPatterns("/graphql/*");
		// registration.addInitParameter("paramName", "paramValue");
		registration.setName("jwtValidFilter");
		registration.setOrder(1);
		return registration;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public Filter getJwtFilter() {
		return new JWTFilter();
	}

	public Filter getJwtValidFilter() {
		return new JWTValidFilter();
	}
	
	@Bean
	public Mailer mailer() {
		return new Mailer();
	}

}
