package ar.com.tandilweb.byo.backend.Transport;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.mysql.cj.log.Log;

import ar.com.tandilweb.byo.backend.Gateway.fcm.FirebaseCloudMessaging;
import ar.com.tandilweb.byo.backend.Model.domain.Events;
import ar.com.tandilweb.byo.backend.Model.domain.Stands;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.EventsRepository;
import ar.com.tandilweb.byo.backend.Model.repository.GpsDataRepository;
import ar.com.tandilweb.byo.backend.Presentation.GQLServices.EventServiceGQL;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.EventDTO;




public class EventsAdapter {
	@Value("${fcm.serverkey}")
	private String serverKey;
	@Autowired
	private FirebaseCloudMessaging firebaseCloudMessaging;
	@Autowired
	private EventsRepository eventsRepository;
	@Autowired
	private GpsDataRepository gpsDR;
	private final double RADIO = 0.15d;//radio de distancia entre evento y usuario

	private static final Logger log = LoggerFactory.getLogger(EventsAdapter.class);
	
	public List<EventDTO> getEvents(Users me) {
		List<Events> events =  eventsRepository.getEvents();
		List<EventDTO> eventsOut = new ArrayList<EventDTO>();
		
		
		for(Events event: events) {
			if(this.compareDistance(me, event) ) {
			EventDTO dto = new EventDTO();
			dto.setEnd_date(event.getEnd_date());
			dto.setGps_data(event.getGps_data());
			dto.setId_event(event.getId_event());
			dto.setLocation_description(event.getLocation_description());
			dto.setLogo(event.getLogo());
			dto.setName(event.getName());
			dto.setStart_date(event.getStart_date());
			List<Stands> stands = eventsRepository.getStands(event.getId_event());
			dto.setStands(stands);
			dto.setCheckins(eventsRepository.getCheckins(me.getId_user()));
			eventsOut.add(dto);}
		}
		
		return eventsOut;
	}
	
	private boolean compareDistance(Users me, Events event) {
		log.debug("USERRR: : " +me.getId_user() );
		log.debug("gpsDR.findUserGpsData(me.getId_user()).getLatitude(): "+ gpsDR.findUserGpsData(me.getId_user()).getLatitude());
		log.debug("event.getGps_data().getLatitude(): " + event.getGps_data().getLatitude());
		if((gpsDR.findUserGpsData(me.getId_user()).getLatitude()-
				event.getGps_data().getLatitude() < this.RADIO) && 
				(gpsDR.findUserGpsData(me.getId_user()).getLatitude()-
						event.getGps_data().getLatitude() > (-this.RADIO))
				&& (gpsDR.findUserGpsData(me.getId_user()).getLongitude()-
						event.getGps_data().getLongitude() < this.RADIO) && 
						(gpsDR.findUserGpsData(me.getId_user()).getLongitude()-
								event.getGps_data().getLongitude() > (-this.RADIO))) {
			log.debug("RETURNN TRUEEEE");
			return true;
			
		}
		return false;
	}
	

	public List<Stands> getStands(Long event) {
		
		return eventsRepository.getStands(event);
	}
	
	
}
