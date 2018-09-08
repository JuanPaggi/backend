package ar.com.tandilweb.byo.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.com.tandilweb.byo.backend.Transport.UserAdapter;

@Configuration
public class TransportFactory {
	
	@Bean
	public UserAdapter userAdapter() {
		return new UserAdapter();
	}

}
