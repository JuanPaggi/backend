package ar.com.tandilweb.byo.backend.Model.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Events {
	
	@Id
	private long id_event;
	
	@NotNull
	private Date start_date;
	
	@NotNull
	private Date end_date;
	
	@NotNull
	private String name;
	
	@NotNull
	private String logo;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_gps_record")
	private GpsData gps_data;
	
	@NotNull
	private String location_description;

	public long getId_event() {
		return id_event;
	}

	public void setId_event(long id_event) {
		this.id_event = id_event;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
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

	public GpsData getGps_data() {
		return gps_data;
	}

	public void setGps_data(GpsData gps_data) {
		this.gps_data = gps_data;
	}

	public String getLocation_description() {
		return location_description;
	}

	public void setLocation_description(String location_description) {
		this.location_description = location_description;
	}
	
}
