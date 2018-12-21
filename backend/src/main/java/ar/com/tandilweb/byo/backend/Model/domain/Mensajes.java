package ar.com.tandilweb.byo.backend.Model.domain;

import java.util.Date;

public class Mensajes {
	
	public long id_sender;
	public long id_target;
	public String message;
	public Date fecha;
	
	public Mensajes() {
		
	}
	
	public Mensajes(long id_sender, long id_target, String message, Date fecha) {
		this.id_sender = id_sender;
		this.id_target = id_target;
		this.message = message;
		this.fecha = fecha;
	}

}
