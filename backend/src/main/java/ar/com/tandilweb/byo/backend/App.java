package ar.com.tandilweb.byo.backend;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import ar.com.tandilweb.byo.backend.Filters.CorsFilter;

@SpringBootApplication
@ComponentScan({"ar.com.tandilweb.byo.backend.Presentation.GQLServices", "ar.com.tandilweb.byo.backend.Presentation.RestServices"})
@Import({TransportFactory.class, ModelFactory.class})
public class App 
{
    public static void main( String[] args ) {
    	SpringApplication.run(App.class, args);
    }
    
    @Bean
	public FilterRegistrationBean<Filter> registerCorsFilter() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(corsFilter());
		registration.addUrlPatterns("/*");
		// registration.addInitParameter("paramName", "paramValue");
		registration.setName("corsFilter");
		registration.setOrder(1);
		return registration;
	}
    public Filter corsFilter() {
		return new CorsFilter();
	}
    
}
