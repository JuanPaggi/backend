package ar.com.tandilweb.byo.backend.Presentation.dto.out;

import java.util.Calendar;

import io.leangen.graphql.annotations.GraphQLQuery;

public class EventDTO {
	
	@GraphQLQuery(name = "id_event")
	private long id_event;
	
	@GraphQLQuery(name = "start_date")
	private Calendar start_date;
	
	@GraphQLQuery(name = "end_date")
	private Calendar end_date;
	
	@GraphQLQuery(name = "fecha_start")
	private String fecha_start;
	
	@GraphQLQuery(name = "fecha_end")
	private String fecha_end;
	
	@GraphQLQuery(name = "name")
	private String name;
	
	@GraphQLQuery(name = "logo")
	private String logo;
	
	@GraphQLQuery(name = "gps_data")
	private int gps_data;
	
	@GraphQLQuery(name = "location_description")
	private String location_description;

	public long getId_event() {
		return id_event;
	}

	public void setId_event(long id_event) {
		this.id_event = id_event;
	}

	public Calendar getStart_date() {
		return start_date;
	}

	public void setStart_date(Calendar start_date) {
		this.start_date = start_date;
	}

	public Calendar getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Calendar end_date) {
		this.end_date = end_date;
	}

	public String getFecha_start() {
		return fecha_start;
	}

	public void setFecha_start(String fecha_start) {
		this.fecha_start = fecha_start;
	}

	public String getFecha_end() {
		return fecha_end;
	}

	public void setFecha_end(String fecha_end) {
		this.fecha_end = fecha_end;
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

	public int getGps_data() {
		return gps_data;
	}

	public void setGps_data(int gps_data) {
		this.gps_data = gps_data;
	}

	public String getLocation_description() {
		return location_description;
	}

	public void setLocation_description(String location_description) {
		this.location_description = location_description;
	}
	

}
