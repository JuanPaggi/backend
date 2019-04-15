package ar.com.tandilweb.byo.backend.Presentation.dto.out;



import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO.Code;
import io.leangen.graphql.annotations.GraphQLQuery;

public class InteraccionesDTO {
	
	@GraphQLQuery(name = "mensajes") 
	private int mensajes;

	@GraphQLQuery(name = "solicitudes")
	private int solicitudes;
	
	@GraphQLQuery(name = "chats") 
	private int chats;
	
	@GraphQLQuery(name = "code") 
	private Code code;
	
	@GraphQLQuery(name = "description") 
	private String description;

	public Code getCode() {
		return code;
	}

	public void setCode(Code internalServerError) {
		this.code = internalServerError;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
