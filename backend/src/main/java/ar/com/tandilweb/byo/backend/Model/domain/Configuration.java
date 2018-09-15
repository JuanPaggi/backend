package ar.com.tandilweb.byo.backend.Model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class Configuration{
	
	@OneToOne()
	@JoinColumn(name="id_user")
	private Users user;
	
	private int kms_radio;
	private boolean linkedin_autoupdate;
	
	public Users getId_usuario() {
		return user;
	}
	public void setId_usuario(Users id_usuario) {
		this.user = id_usuario;
	}
	public int getKms_radio() {
		return kms_radio;
	}
	public void setKms_radio(int kms_radio) {
		this.kms_radio = kms_radio;
	}
	public boolean isLinkedin_autoupdate() {
		return linkedin_autoupdate;
	}
	public void setLinkedin_autoupdate(boolean linkedin_autoupdate) {
		this.linkedin_autoupdate = linkedin_autoupdate;
	}
	
	
	
}
