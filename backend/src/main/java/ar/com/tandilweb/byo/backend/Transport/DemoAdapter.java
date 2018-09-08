package ar.com.tandilweb.byo.backend.Transport;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.tandilweb.byo.backend.Gateway.DemoConsumer;
import ar.com.tandilweb.byo.backend.Gateway.dto.Example;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.Hola;

public class DemoAdapter {
	
	@Autowired
	public DemoConsumer demoConsumer;
	
	public Hola getApiDemo() {
		Hola out = new Hola();
		Example response = demoConsumer.getData();
		out.setMundo(response.getValue().getQuote());
		return out;
	}

}
