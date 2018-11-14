package ar.com.tandilweb.byo.backend.Presentation.RestServices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.tandilweb.byo.backend.Gateway.fcm.FirebaseCloudMessaging;
import ar.com.tandilweb.byo.backend.Gateway.fcm.HttpFCMPayload;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.UserRepository;

@RestController
@RequestMapping("/demoService")
public class DemoService {
	
	@Value("${fcm.serverkey}")
	private String serverKey;
	
	@Autowired
	private FirebaseCloudMessaging fcm;
	
	@Autowired
	private UserRepository urepo;
	
	@RequestMapping(path="/info", method=RequestMethod.GET) 
	public String informationService() {
		return "hola mundo";
	}
	
	@RequestMapping(path="/notify", method=RequestMethod.GET)
	public boolean sendNotification() {
		HttpFCMPayload payload = new HttpFCMPayload();
		fcm.setServerKey(serverKey);
		List<String> rids = new ArrayList<String>();
		Users user = urepo.findById(25L);
		rids.add(user.getFcmToken());
		payload.setRegistration_ids(rids);
		//payload.setTopic('generalTopic');
		fcm.send(payload);
		return true;
	}
}
