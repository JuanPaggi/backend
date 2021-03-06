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
	private String url;
	private String nombre_lugar;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getNombre_lugar() {
		return nombre_lugar;
	}
	public void setNombre_lugar(String nombre_lugar) {
		this.nombre_lugar = nombre_lugar;
	}
	private String location_description;
	private List<Stands> stands;
	private Long id_gps_record;
	public Long getId_gps_record() {
		return id_gps_record;
	}
	public void setId_gps_record(Long id_gps_record) {
		this.id_gps_record = id_gps_record;
	}
	private double radio;
	private boolean dentro_radio = false;

	public boolean isDentro_radio() {
		return dentro_radio;
	}
	public void setDentro_radio(boolean dentro_radio) {
		this.dentro_radio = dentro_radio;
	}
	public void setRadio(double radio) {
		this.radio = radio;
	}
	public List<Stands> getStands() {
		return stands;
	}
	public void setStands(List<Stands> stands) {
		this.stands = stands;
	}
	public Events(long id_event, Calendar start_date, Calendar end_date, String name, String logo, double radio,
			String location_description, List<Stands> stands, Long id_gps_record, String url, String nombre_lugar) {
		this.id_event = id_event;
		this.start_date = start_date;
		this.end_date = end_date;
		this.name = name;
		this.logo = logo;
		this.radio = radio;
		this.location_description = location_description;
		this.stands = stands;
		this.id_gps_record = id_gps_record;
		this.url = url;
		this.nombre_lugar = nombre_lugar;
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


	public String getLocation_description() {
		return location_description;
	}

	public void setLocation_description(String location_description) {
		this.location_description = location_description;
	}
	public double getRadio() {
		// TODO Auto-generated method stub
		return this.radio;
	}
	
}
