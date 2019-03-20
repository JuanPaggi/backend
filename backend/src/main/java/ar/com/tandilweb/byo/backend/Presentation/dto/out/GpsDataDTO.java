package ar.com.tandilweb.byo.backend.Presentation.dto.out;

import java.util.Date;

import io.leangen.graphql.annotations.GraphQLQuery;

public class GpsDataDTO {
	@GraphQLQuery(name = "id_gps_record")
	private long id_gps_record;
	@GraphQLQuery(name = "latitude")
	private double latitude;
	@GraphQLQuery(name = "longitude")
	private double longitude;
	@GraphQLQuery(name = "date_recorded")
	private Date date_recorded;
	@GraphQLQuery(name = "distance")
	private double distance;
	
	public GpsDataDTO(){}
	
	public GpsDataDTO(double latitude, double longitude, Date date_recorded) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.date_recorded = date_recorded;
	}
	
	public GpsDataDTO(long id_gps_record, double latitude, double longitude, Date date_recorded){
		this.id_gps_record = id_gps_record;
		this.latitude = latitude;
		this.longitude = longitude;
		this.date_recorded = date_recorded;
	}
	public GpsDataDTO( double latitude, double longitude, double distance){
		this.latitude = latitude;
		this.longitude = longitude;
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

	public Date getDate_recorded() {
		return date_recorded;
	}

	public void setDate_recorded(Date date_recorded) {
		this.date_recorded = date_recorded;
	}
}
