package ar.com.tandilweb.byo.backend.Presentation.dto.out;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ar.com.tandilweb.byo.backend.Model.domain.GpsData;
import ar.com.tandilweb.byo.backend.Model.domain.Stands;
import ar.com.tandilweb.byo.backend.Model.domain.StandsCheckin;
import io.leangen.graphql.annotations.GraphQLQuery;

public class EventDTO {
	
	@GraphQLQuery(name = "id_event") 
	private long id_event;

	@GraphQLQuery(name = "start_date")
	private Date start_date;
	
	@GraphQLQuery(name = "end_date") 
	private Date end_date;
	
	@GraphQLQuery(name = "name")
	private String name;
	
	@GraphQLQuery(name = "logo") 
	private String logo;
	
	@GraphQLQuery(name = "gps_data")
	private GpsDataDTO gps_data = new GpsDataDTO();
	
	@GraphQLQuery(name = "location_description") 
	private String location_description;
	
	@GraphQLQuery(name = "stands")
	private List<Stands> stands;
	
	@GraphQLQuery(name = "checkins")
	private List<StandsCheckin> checkins;
	
	@GraphQLQuery(name = "radio")
	private double radio;
	
	@GraphQLQuery(name = "dentro_radio")
	private boolean dentro_radio;
	
	@GraphQLQuery(name = "got_ticket")
	private boolean got_ticket = false;
	
	public boolean isDentro_radio() {
		return dentro_radio;
	}

	public void setDentro_radio(boolean dentro_radio) {
		this.dentro_radio = dentro_radio;
	}

	
	public boolean getGot_ticket() {
		return this.got_ticket;
	}

	public void setGot_ticket(boolean gotTicket) {
		this.got_ticket= gotTicket;
	}

	public void setGps_data(GpsDataDTO gps_data) {
		this.gps_data = gps_data;
	}
	
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}



	public double getRadio() {
		return radio;
	}

	public void setRadio(double radio) {
		this.radio = radio;
	}

	public List<StandsCheckin> getCheckins() {
		return checkins;
	}

	public void setCheckins(List<StandsCheckin> checkins) {
		this.checkins = checkins;
	}

	public List<Stands> getStands() {
		return stands;
	}

	public void setStands(List<Stands> stands) {
		this.stands = stands;
	}

	public long getId_event() {
		return id_event;
	}

	public void setId_event(long id_event) {
		this.id_event = id_event;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Calendar start_date) {
		this.start_date = start_date.getTime();
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Calendar end_date) {
		this.end_date = end_date.getTime();
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public GpsDataDTO getGps_data() {
		return gps_data;
	}

	public void setGps_data(GpsData gps_data) {

		this.gps_data.setId_gps_record(gps_data.getId_gps_record()) ;
		this.gps_data.setLatitude(gps_data.getLatitude());
		this.gps_data.setLongitude(gps_data.getLongitude());
		this.gps_data.setDate_recorded(gps_data.getDate_recorded());
	}

	public String getLocation_description() {
		return location_description;
	}

	public void setLocation_description(String location_description) {
		this.location_description = location_description;
	}
	

}
