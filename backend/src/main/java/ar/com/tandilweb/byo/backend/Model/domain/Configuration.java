package ar.com.tandilweb.byo.backend.Model.domain;

public class Configuration{
	
	private Users user;
	private long id_user;
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
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public long getId_user() {
		return id_user;
	}
	public void setId_user(long id_user) {
		this.id_user = id_user;
	}
	
	
	
}
