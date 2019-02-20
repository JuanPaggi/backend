package ar.com.tandilweb.byo.backend.Gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import ar.com.tandilweb.byo.backend.Gateway.dto.AccessTokenLinkedin;
import ar.com.tandilweb.byo.backend.Gateway.dto.LinkedInProfile;

public class LinkedInConsumer {
	
	private static final Logger log = LoggerFactory.getLogger(LinkedInConsumer.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	public LinkedInProfile checkAccessToken(String accessToken) {
		log.debug("Linkedin Token Validator: "+accessToken);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer "+accessToken);
		HttpEntity entity = new HttpEntity(headers);
		LinkedInProfile response = restTemplate.exchange(
				"https://api.linkedin.com/v1/people/~:(id,first-name,last-name,headline,picture-url,location,industry,summary,positions,specialties,public-profile-url,email-address)?format=json", HttpMethod.GET, entity, LinkedInProfile.class).getBody();
		log.debug("-Linkedin Response-"); 
		return response;
	}
	
	public String getAccessToken(String code) {
		log.debug("Linkedin get token for code: "+code);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/x-www-form-urlencoded");
		HttpEntity entity = new HttpEntity("grant_type=authorization_code&code="+code+"&redirect_uri=http://localhost&client_id=789jcqwn80fy1t&client_secret=30oh7iQOPQ97ZfX6", headers);
		AccessTokenLinkedin response = restTemplate.exchange("https://www.linkedin.com/oauth/v2/accessToken", HttpMethod.POST, entity, AccessTokenLinkedin.class).getBody();
		return response.access_token;
	}
}
