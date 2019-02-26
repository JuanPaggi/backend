package ar.com.tandilweb.byo.backend.Model.domain;

import java.util.Calendar;
import java.util.List;

public class Events {

	private long id_event;
	private Calendar start_date;
	private Calendar end_date;
	private String fecha_start;
	private String fecha_end;
	private String name;
	private String logo;
	private int gps_data;
	private String location_description;
	private List<Stands> stands;

	public List<Stands> getStands() {
		return stands;
	}
	public void setStands(List<Stands> stands) {
		this.stands = stands;
	}
	public Events(long id_event, Calendar start_date, Calendar end_date, String name, String logo, int gps,
			String location_description, List<Stands> stands) {
		this.id_event = id_event;
		this.start_date = start_date;
		this.end_date = end_date;
		this.name = name;
		this.logo = logo;
		this.gps_data = gps;
		this.location_description = location_description;
		this.stands = stands;
	}

	public long getId_event() {
		return id_event;
	}

	public void setId_event(long id_event) {
		this.id_event = id_event;
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
