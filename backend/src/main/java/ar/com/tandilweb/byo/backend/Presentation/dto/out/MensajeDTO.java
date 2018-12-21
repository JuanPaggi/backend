package ar.com.tandilweb.byo.backend.Presentation.dto.out;

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

}
