package ar.com.tandilweb.byo.backend.Model.domain;

import java.io.Serializable;
import java.util.Calendar;

public class StandsCheckin implements Serializable{

	private static final long serialVersionUID = 1L;
	
	Long id_stand;
	Long id_user;
	Calendar date_checkin;
	
	public StandsCheckin(Long id_stand, Long id_user, Calendar date_checkin) {
		this.id_stand = id_stand;
		this.id_user = id_user;
		this.date_checkin = date_checkin;
	}

	public Long getId_stand() {
		return id_stand;
	}

	public void setId_stand(Long id_stand) {
		this.id_stand = id_stand;
	}

	public Long getId_user() {
		return id_user;
	}

	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}

	public Calendar getDate_checkin() {
		return date_checkin;
	}

	public void setDate_checkin(Calendar date_checkin) {
		this.date_checkin = date_checkin;
	}
	
}
