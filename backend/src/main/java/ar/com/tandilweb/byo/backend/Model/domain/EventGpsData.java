package ar.com.tandilweb.byo.backend.Model.domain;



public class EventGpsData {
	private long id_gps_record;
	private double latitude;
	private double longitude;
	private double distance;

	
	public EventGpsData(){}
	
	public EventGpsData(double latitude, double longitude, double distance) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.distance = distance;
	}
	
	public EventGpsData(long id_gps_record, double latitude, double longitude, double distance){
		this.id_gps_record = id_gps_record;
		this.latitude = latitude;
		this.longitude = longitude;
		this.distance = distance;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
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


}
