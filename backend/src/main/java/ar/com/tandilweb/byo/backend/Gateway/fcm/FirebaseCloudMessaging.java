package ar.com.tandilweb.byo.backend.Gateway.fcm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class FirebaseCloudMessaging {
	
	private static final Logger log = LoggerFactory.getLogger(FirebaseCloudMessaging.class);
	
	private String endpoint = "https://fcm.googleapis.com/fcm/send";
//	private String serverKey;
	private String lastError;
//	private int timeOut = 6000;
	
	@Autowired
	RestTemplate restTemplate;
	
	private HttpHeaders headers = new HttpHeaders();
	
	public FirebaseCloudMessaging() {
	}
	
	public void setServerKey(String serverKey) {
//		this.serverKey = serverKey;
		headers.set("Content-Type", "application/json");
		headers.set("Authorization", "key="+serverKey);
	}
	
	public String send(Fcmp payload) {
		log.debug("Fcm sending payload", payload);
		HttpEntity<String> entity = new HttpEntity<String>(payload.getBody(), headers);
		String response = restTemplate.exchange(
			endpoint, HttpMethod.POST, entity, String.class).getBody();
		log.debug("-FCM Response-"); 
		return response;
	}
	
	public String getError() {
		return this.lastError;
	}
	
}
