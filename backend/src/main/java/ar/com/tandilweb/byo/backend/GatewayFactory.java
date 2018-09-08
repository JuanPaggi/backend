package ar.com.tandilweb.byo.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.com.tandilweb.byo.backend.Gateway.DemoConsumer;

@Configuration
public class GatewayFactory {
	
	@Bean
	public DemoConsumer demoConsumer() {
		return new DemoConsumer();
	}

}
