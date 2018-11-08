package ar.com.tandilweb.byo.backend.Model.domain;

import java.util.Date;

public class GpsData {

	private long id_gps_record;
	private double latitude;
	private double longitude;
	private Date date_recorded;
	
	public GpsData(){}
	
	public GpsData(double latitude, double longitude, Date date_recorded) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.date_recorded = date_recorded;
	}
	
	public GpsData(long id_gps_record, double latitude, double longitude, Date date_recorded){
		this.id_gps_record = id_gps_record;
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
	
}
