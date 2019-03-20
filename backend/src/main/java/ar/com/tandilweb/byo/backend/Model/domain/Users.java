package ar.com.tandilweb.byo.backend.Model.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class Users {

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

	private String picture_url;

	private boolean premium;

	private String salt_jwt;

	private boolean completoByO;
	
	private boolean receive_notifications;

	private boolean locked;

	private int failedLoginAttempts;

	private String unLockAccountCode;
	
	private String fcmToken;

//	private Set<GpsData> gps_datas = new HashSet<GpsData>();
//
//	private RememberTokens rememberToken;
	
	//CONSTRUCTORS
	public Users() {}
	
	public Users(Long id_user, String firstName, String lastName, String email, String password, Date last_login, Date signup_date,
			String linkedinId, String busco, String ofrezco, String picture_url, boolean premium, String salt_jwt,
			boolean completoByO, boolean receive_notifications, boolean locked, int failedLoginAttempts, String unLockAccountCode, String fcmToken) {
		this.id_user = id_user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.last_login = last_login;
		this.signup_date = signup_date;
		this.linkedinId = linkedinId;
		this.busco = busco;
		this.ofrezco = ofrezco;
		this.picture_url = picture_url;
		this.premium = premium;
		this.salt_jwt = salt_jwt;
		this.completoByO = completoByO;
		this.receive_notifications = receive_notifications;
		this.locked = locked;
		this.failedLoginAttempts = failedLoginAttempts;
		this.unLockAccountCode = unLockAccountCode;
		this.fcmToken = fcmToken;
	}
	
	//METHODS
//	public void addGps(GpsData gps) {
//		gps_datas.add(gps);
//		gps.getUsers().add(this);
//	}
//
//	public void removeGps(GpsData gps) {
//		gps_datas.remove(gps);
//		gps.getUsers().remove(this);
//	}

	//GETTERS & SETTERS
	public String getBusco() {
		return busco;
	}
	public String getEmail() {
		return email;
	}
	public int getFailedLoginAttempts() {
		return failedLoginAttempts;
	}
	public String getFirst_name() {
		return firstName;
	}
	public String getFirstName() {
		return firstName;
	}
//	public Set<GpsData> getGps_datas() {
//		return gps_datas;
//	}
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

	public String getOfrezco() {
		return ofrezco;
	}
	public String getPassword() {
		return password;
	}
	public String getPicture_url() {
		return picture_url;
	}
//	public RememberTokens getRememberToken() {
//		return rememberToken;
//	}
	public String getSalt_jwt() {
		return salt_jwt;
	}
	public Date getSignup_date() {
		return signup_date;
	}
	public String getUnLockAccountCode() {
		return unLockAccountCode;
	}
	public boolean isCompletoByO() {
		return completoByO;
	}
	public boolean getReceiveNotifications() {
		return this.receive_notifications;
	}
	public boolean isLocked() {
		return locked;
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
	public void setFailedLoginAttempts(int failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}
	public void setFirst_name(String first_name) {
		this.firstName = first_name;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
//	public void setGps_datas(Set<GpsData> gps_datas) {
//		this.gps_datas = gps_datas;
//	}
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

	public void setLocked(boolean locked) {
		this.locked = locked;
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
	public void setPremium(boolean premium) {
		this.premium = premium;
	}
//	public void setRememberToken(RememberTokens rememberToken) {
//		this.rememberToken = rememberToken;
//	}
	public void setSalt_jwt(String salt_jwt) {
		this.salt_jwt = salt_jwt;
	}
	public void setSignup_date(Date signup_date) {
		this.signup_date = signup_date;
	}
	public void setUnLockAccountCode(String unLockAccountCode) {
		this.unLockAccountCode = unLockAccountCode;
	}

	public void setReceiveNotifications(boolean receive_notifications) {
		this.receive_notifications = receive_notifications;
	}
	
	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}
}
