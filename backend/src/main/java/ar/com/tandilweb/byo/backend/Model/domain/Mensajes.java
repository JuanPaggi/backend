package ar.com.tandilweb.byo.backend.Model.domain;

import java.util.Calendar;
import java.util.Date;

public class Mensajes {
	
	public long id_message;
	public long id_sender;
	public long id_target;
	public String message;
	public Date fecha;
	public String fechaStr;
	public boolean is_viewed;
	
	public Mensajes() {
		
	}
	
	public Mensajes(long id_message, long id_sender, long id_target, String message, Date fecha, boolean is_viewed) {
		this.id_sender = id_sender;
		this.id_target = id_target;
		this.message = message;
		this.fecha = fecha;
		this.is_viewed = is_viewed;
		this.id_message = id_message;
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.fecha);
		this.fechaStr = cal.get(Calendar.DATE)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR);
	}

}
