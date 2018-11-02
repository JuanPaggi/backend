package ar.com.tandilweb.byo.backend.Model.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class GpsData {
	
	@Id
	private long id_gps_record;
	
	@NotNull
	private double latitude;
	
	@NotNull
	private double longitude;
	
	@NotNull
	private Date date_recorded;
	
	@NotNull
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
                },
            mappedBy = "gps_datas")
	private Set<Users> users = new HashSet<Users>();
	
	public GpsData(){}
	
	public GpsData(double latitude, double longitude, Date date_recorded) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.date_recorded = date_recorded;
	}

	public long getId_gps_record() {
		return id_gps_record;
	}

	public void setId_gps_record(long id_gps_record) {
		this.id_gps_record = id_gps_record;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Date getDate_recorded() {
		return date_recorded;
	}

	public void setDate_recorded(Date date_recorded) {
		this.date_recorded = date_recorded;
	}
	
	public Set<Users> getUsers() {
		return users;
	}

	public void setUsers(Set<Users> users) {
		this.users = users;
	}
	
}
