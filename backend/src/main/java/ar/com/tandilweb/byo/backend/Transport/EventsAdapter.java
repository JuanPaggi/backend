package ar.com.tandilweb.byo.backend.Transport;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import ar.com.tandilweb.byo.backend.Gateway.fcm.FirebaseCloudMessaging;
import ar.com.tandilweb.byo.backend.Model.domain.Events;
import ar.com.tandilweb.byo.backend.Model.domain.Stands;
import ar.com.tandilweb.byo.backend.Model.repository.EventsRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.EventDTO;




public class EventsAdapter {
	@Value("${fcm.serverkey}")
	private String serverKey;
	@Autowired
	private FirebaseCloudMessaging firebaseCloudMessaging;
	@Autowired
	private EventsRepository eventsRepository;

	public List<EventDTO> getEvents() {
		List<Events> events =  eventsRepository.getEvents();
		List<EventDTO> eventsOut = new ArrayList<EventDTO>();
		
		for(Events event: events) {
			EventDTO dto = new EventDTO();
			dto.setEnd_date(event.getEnd_date());
			dto.setFecha_end(event.getFecha_end());
			dto.setFecha_start(event.getFecha_start());
			dto.setGps_data(event.getGps_data());
			dto.setId_event(event.getId_event());
			dto.setLocation_description(event.getLocation_description());
			dto.setLogo(event.getLogo());
			dto.setName(event.getName());
			dto.setStart_date(event.getStart_date());
			eventsOut.add(dto);
		}
		
		return eventsOut;
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
