package ar.com.tandilweb.byo.backend.Model.domain;

public class Stands {

	private long id_stand;
	private Events id_event;
	private String name;
	private String logo;
	private Users id_user_organizer;
	
	public long getId_stand() {
		return id_stand;
	}
	public void setId_stand(long id_stand) {
		this.id_stand = id_stand;
	}
	public Events getId_event() {
		return id_event;
	}
	public void setId_event(Events id_event) {
		this.id_event = id_event;
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
	public Users getId_user_organizer() {
		return id_user_organizer;
	}
	public void setId_user_organizer(Users id_user_organizer) {
		this.id_user_organizer = id_user_organizer;
	}
	
}
