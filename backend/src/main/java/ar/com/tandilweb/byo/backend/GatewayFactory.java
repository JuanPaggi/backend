package ar.com.tandilweb.byo.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.com.tandilweb.byo.backend.Gateway.DemoConsumer;
import ar.com.tandilweb.byo.backend.Gateway.LinkedInConsumer;

@Configuration
public class GatewayFactory {
	
	@Bean
	public DemoConsumer demoConsumer() {
		return new DemoConsumer();
	}
	
	@Bean
	public LinkedInConsumer linkedinConsumer() {
		return new LinkedInConsumer();
	}

}
