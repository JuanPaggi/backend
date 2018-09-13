package ar.com.tandilweb.byo.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.com.tandilweb.byo.backend.Transport.LinkedInAdapter;

@Configuration
public class TransportFactory {
	
	@Bean
	public LinkedInAdapter linkedinAdapter() {
		return new LinkedInAdapter();
	}
}
