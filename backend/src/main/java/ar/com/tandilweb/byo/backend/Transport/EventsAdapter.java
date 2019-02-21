package ar.com.tandilweb.byo.backend.Transport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import ar.com.tandilweb.byo.backend.Gateway.fcm.FirebaseCloudMessaging;
import ar.com.tandilweb.byo.backend.Model.domain.Events;
import ar.com.tandilweb.byo.backend.Model.domain.Stands;
import ar.com.tandilweb.byo.backend.Model.repository.EventsRepository;
import ar.com.tandilweb.byo.backend.Model.repository.FriendshipsRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.VCardEvento;




public class EventsAdapter {
	@Value("${fcm.serverkey}")
	private String serverKey;
	@Autowired
	private FirebaseCloudMessaging firebaseCloudMessaging;
	@Autowired
	private EventsRepository eventsRepository;

	public List<Events> getEvents() {
		
		return eventsRepository.getEvents();
	}
	public List<Stands> getStands(Long event) {
		
		return eventsRepository.getStands(event);
	}
	public Boolean getCheckin(Integer stand, long id_user) {
		
		Integer n = eventsRepository.getCheckin(stand,id_user);
		boolean checkin = (!n.equals(0)) && (n != null);
		return   checkin;
	}
	
}
