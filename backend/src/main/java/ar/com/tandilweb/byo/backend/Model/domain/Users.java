package ar.com.tandilweb.byo.backend.Model.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Users {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id_user;
	@NotNull
	private String first_name;
	@NotNull
	private String last_name;
	@NotNull
	private String email;
	@NotNull
	private long last_login;
	@NotNull
	private Date signup_date;

	private String linkedin_id;
	
	@NotNull
	private String busco;
	@NotNull
	private String ofrezco;
	@NotNull
	private String picture_url;
	@NotNull
	private boolean is_premium;
	@NotNull
	private String salt_jwt;
	
	public long getId_user() {
		return id_user;
	}
	public void setId_user(long id_user) {
		this.id_user = id_user;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getLast_login() {
		return last_login;
	}
	public void setLast_login(long last_login) {
		this.last_login = last_login;
	}
	public Date getSignup_date() {
		return signup_date;
	}
	public void setSignup_date(Date signup_date) {
		this.signup_date = signup_date;
	}
	public String getLinkedin_id() {
		return linkedin_id;
	}
	public void setLinkedin_id(String linkedin_id) {
		this.linkedin_id = linkedin_id;
	}
	public String getBusco() {
		return busco;
	}
	public void setBusco(String busco) {
		this.busco = busco;
	}
	public String getOfrezco() {
		return ofrezco;
	}
	public void setOfrezco(String ofrezco) {
		this.ofrezco = ofrezco;
	}
	public String getPicture_url() {
		return picture_url;
	}
	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}
	public boolean isIs_premium() {
		return is_premium;
	}
	public void setIs_premium(boolean is_premium) {
		this.is_premium = is_premium;
	}
	public String getSalt_jwt() {
		return salt_jwt;
	}
	public void setSalt_jwt(String salt_jwt) {
		this.salt_jwt = salt_jwt;
	}
}
