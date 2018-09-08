package ar.com.tandilweb.byo.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.com.tandilweb.byo.backend.Transport.DemoAdapter;
import ar.com.tandilweb.byo.backend.Transport.TablaTestAdapter;
import ar.com.tandilweb.byo.backend.Transport.UsuariosAdapter;

@Configuration
public class TransportFactory {
	
	@Bean
	public TablaTestAdapter userAdapter() {
		return new TablaTestAdapter();
	}

	@Bean
	public DemoAdapter demoAdapter() {
		return new DemoAdapter();
	}
	
	@Bean
	public UsuariosAdapter usuariosAdapter() {
		return new UsuariosAdapter();
	}
}
