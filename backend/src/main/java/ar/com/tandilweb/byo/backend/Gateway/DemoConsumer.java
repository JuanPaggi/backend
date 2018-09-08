package ar.com.tandilweb.byo.backend.Gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import ar.com.tandilweb.byo.backend.Gateway.dto.Example;

public class DemoConsumer {
	
	@Autowired
	RestTemplate restTemplate;
	
	public Example getData() {
        Example data = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Example.class);
        return data;
	}

}
