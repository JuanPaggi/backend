package ar.com.tandilweb.byo.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ar.com.tandilweb.byo.backend.Transport.ChatAdapter;
import ar.com.tandilweb.byo.backend.Transport.ConfigurationAdapter;
import ar.com.tandilweb.byo.backend.Transport.FriendshipsAdapter;
import ar.com.tandilweb.byo.backend.Transport.GpsAdapter;
import ar.com.tandilweb.byo.backend.Transport.LinkedInAdapter;
import ar.com.tandilweb.byo.backend.Transport.StandsAdapter;
import ar.com.tandilweb.byo.backend.Transport.UserAdapter;

@Configuration
public class TransportFactory {
	
	@Bean
	public LinkedInAdapter linkedinAdapter() {
		return new LinkedInAdapter();
	}
	
	@Bean
	public UserAdapter UserAdapter() {
		return new UserAdapter();
	}
	
	@Bean
	public ChatAdapter chatAdapter() {
		return new ChatAdapter();
	}
	
	@Bean
	public ConfigurationAdapter configurationAdapter() {
		return new ConfigurationAdapter();
	}
	
	@Bean
	public FriendshipsAdapter friendshipsAdapter() {
		return new FriendshipsAdapter();
	}
	
	@Bean
	public GpsAdapter gpsAdapter() {
		return new GpsAdapter();
	}
	
	@Bean
	public StandsAdapter standsAdapter() {
		return new StandsAdapter();
	}
}
