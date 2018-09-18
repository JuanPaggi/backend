package ar.com.tandilweb.byo.backend.Model.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
	@ManyToMany
	@JoinTable(name="gps_data_users",
				joinColumns= {@JoinColumn(name="id_gps_record")},
				inverseJoinColumns= {@JoinColumn(name="id_user")})
	private List<Users> users = new ArrayList<Users>();

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
	
	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}
	
}
