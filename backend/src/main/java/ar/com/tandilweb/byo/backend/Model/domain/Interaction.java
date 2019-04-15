package ar.com.tandilweb.byo.backend.Model.domain;



import javax.validation.constraints.NotNull;

public class Interaction {
	@NotNull
	private long id_user;
	@NotNull
	private int mensajes;
	@NotNull
	private int solicitudes;
	@NotNull
	private int chats;
	
	public Interaction(long id, int mensajes,int solicitudes, int chats) {
		this.id_user = id;
		this.mensajes = mensajes;
		this.solicitudes = solicitudes;
		this.chats = chats;
	}
	
	public long getId_user() {
		return id_user;
	}
	public void setId_user(long id_user) {
		this.id_user = id_user;
	}
	public int getMensajes() {
		return mensajes;
	}
	public void setMensajes(int mensajes) {
		this.mensajes = mensajes;
	}
	public int getSolicitudes() {
		return solicitudes;
	}
	public void setSolicitudes(int solicitudes) {
		this.solicitudes = solicitudes;
	}
	public int getChats() {
		return chats;
	}
	public void setChats(int chats) {
		this.chats = chats;
	}

}