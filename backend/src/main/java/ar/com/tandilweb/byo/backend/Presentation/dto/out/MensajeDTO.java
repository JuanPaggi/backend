package ar.com.tandilweb.byo.backend.Presentation.dto.out;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ar.com.tandilweb.byo.backend.Model.domain.Mensajes;
import io.leangen.graphql.annotations.GraphQLQuery;

public class MensajeDTO {
	
	@GraphQLQuery(name = "id_message")
	public long id_message;
	
	@GraphQLQuery(name = "id_sender")
	public long id_sender;
	
	@GraphQLQuery(name = "id_target")
	public long id_target;
	
	@GraphQLQuery(name = "message")
	public String message;
	
	@GraphQLQuery(name = "fecha")
	public Date fecha;
	
	@GraphQLQuery(name = "is_viewed")
	public boolean is_viewed;
	
	public MensajeDTO() { }
	
	public MensajeDTO(Mensajes mensaje) {
		this.id_message = mensaje.id_message;
		this.id_target = mensaje.id_target;
		this.message = mensaje.message;
		this.fecha = mensaje.fecha;
		this.is_viewed = mensaje.is_viewed;
	}
	
	@GraphQLQuery(name = "fechaStr")
	public String getFormattedTime() {
		return parseDate(fecha);
	}

	public static String parseDate(Date fecha) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date today = c.getTime();
		// TODO: en algun futuro chequear que haya pasado un año y poner el año.
		if(fecha != null && fecha.after(today)) {
			SimpleDateFormat dt = new SimpleDateFormat("hh:mm"); 
			return "hoy";
		} else {
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM"); 
			return "antes";
		}
	}
	
}
