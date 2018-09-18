package ar.com.tandilweb.byo.backend.Model.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class StandsCheckin {
	
	@NotNull
	Stands id_stand;
	@NotNull
	Users id_user;
	
	@NotNull
	Date date_checkin;

	public Stands getId_stand() {
		return id_stand;
	}

	public void setId_stand(Stands id_stand) {
		this.id_stand = id_stand;
	}

	public Users getId_user() {
		return id_user;
	}

	public void setId_user(Users id_user) {
		this.id_user = id_user;
	}

	public Date getDate_checkin() {
		return date_checkin;
	}

	public void setDate_checkin(Date date_checkin) {
		this.date_checkin = date_checkin;
	}
	
}
