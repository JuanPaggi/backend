package ar.com.tandilweb.byo.backend.Transport;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.tandilweb.byo.backend.Gateway.LinkedInConsumer;
import ar.com.tandilweb.byo.backend.Gateway.dto.LinkedInProfile;

public class LinkedInAdapter {
	
	@Autowired
	LinkedInConsumer linkedInConsumer;
	
	public void validateAccessToken(String accessToken) {
		LinkedInProfile reponse = linkedInConsumer.checkAccessToken(accessToken);
		// transferir información a un dto de salida y registrar en la db.
	}

}
