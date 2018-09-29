package ar.com.tandilweb.byo.backend.Model.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Users {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id_user;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String email;
	@NotNull
	private String password;
	@NotNull
	private Date last_login;
	@NotNull
	private Date signup_date;

	private String linkedinId;

	@NotNull
	private String busco;
	@NotNull
	private String ofrezco;
	@NotNull
	private String picture_url;
	@NotNull
	private boolean premium;
	@NotNull
	private String salt_jwt;
	@NotNull
	private boolean completoByO;

	@NotNull
	@ManyToMany
	@JoinTable(name="gps_data_users",
	joinColumns= {@JoinColumn(name="id_user")},
	inverseJoinColumns= {@JoinColumn(name="id_gps_record")})
	private List<GpsData> gps_datas = new ArrayList<GpsData>();

	public String getBusco() {
		return busco;
	}
	public String getEmail() {
		return email;
	}
	public String getFirst_name() {
		return firstName;
	}
	public String getFirstName() {
		return firstName;
	}
	public List<GpsData> getGps_datas() {
		return gps_datas;
	}
	public long getId_user() {
		return id_user;
	}
	public Date getLast_login() {
		return last_login;
	}
	public String getLast_name() {
		return lastName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getLinkedin_id() {
		return linkedinId;
	}
	public String getLinkedinId() {
		return linkedinId;
	}
	public String getOfrezco() {
		return ofrezco;
	}
	public String getPassword() {
		return password;
	}
	public String getPicture_url() {
		return picture_url;
	}
	public String getSalt_jwt() {
		return salt_jwt;
	}
	public Date getSignup_date() {
		return signup_date;
	}
	public boolean isCompletoByO() {
		return completoByO;
	}
	public boolean isPremium() {
		return premium;
	}
	public void setBusco(String busco) {
		this.busco = busco;
	}
	public void setCompletoByO(boolean completoByO) {
		this.completoByO = completoByO;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setFirst_name(String first_name) {
		this.firstName = first_name;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setGps_datas(List<GpsData> gps_datas) {
		this.gps_datas = gps_datas;
	}
	public void setId_user(long id_user) {
		this.id_user = id_user;
	}
	public void setIsPremium(boolean is_premium) {
		this.premium = is_premium;
	}
	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}
	public void setLast_name(String last_name) {
		this.lastName = last_name;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setLinkedin_id(String linkedin_id) {
		this.linkedinId = linkedin_id;
	}
	public void setLinkedinId(String linkedinId) {
		this.linkedinId = linkedinId;
	}
	public void setOfrezco(String ofrezco) {
		this.ofrezco = ofrezco;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}
	public void setSalt_jwt(String salt_jwt) {
		this.salt_jwt = salt_jwt;
	}
	public void setSignup_date(Date signup_date) {
		this.signup_date = signup_date;
	}
	
}
