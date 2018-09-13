package ar.com.tandilweb.byo.backend.Gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import ar.com.tandilweb.byo.backend.Gateway.dto.LinkedInProfile;

public class LinkedInConsumer {
	
	private static final Logger log = LoggerFactory.getLogger(LinkedInConsumer.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	public LinkedInProfile checkAccessToken(String accessToken) {
		log.debug("Linkedin Token Validator: "+accessToken);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer "+accessToken);
		headers.set("x-li-src", "msdk");
		HttpEntity entity = new HttpEntity(headers);
		LinkedInProfile response = restTemplate.exchange(
				"https://api.linkedin.com/v1/people/~:(id,first-name,last-name,headline,picture-url,location,industry,summary,positions,specialties,public-profile-url,email-address)?format=json", HttpMethod.GET, entity, LinkedInProfile.class).getBody();
		log.debug("-Linkedin Response-"); 
		return response;
		
	}
}
