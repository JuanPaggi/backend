package ar.com.tandilweb.byo.backend.Presentation.dto.out;

import io.leangen.graphql.annotations.GraphQLQuery;

public class ResponseDTO {
	
	public enum Code {
		OK (200),
		CREATED (201), // recurso creado
		ACCEPTED (202), 
		BAD_REQUEST (400), // validaciones no pasadas
		NOT_FOUND (404), // algún recurso no fue encontrado
		FORBIDDEN (403), // sin acceso
		METHOD_NOT_IMPLEMENTED (501), // la rama de flujo no está implementada
		UPGRADE_REQUIRED (426), // se está enviando una petición obsoleta o una versión del protocolo obsoleto
		BAD_GATEWAY (503), // si la api a la que queremos consumir no responde correctamente
		AUTHORIZATION_REQUIRED (401), // UNAUTHORIZED, no se están enviando los datos necesarios para autentificar.
		INTERNAL_SERVER_ERROR (500)
		;
		private final int codeNumber;
	    private Code(int codeNumber) {
	        this.codeNumber = codeNumber;
	    }
	    public int getCodeNumber() {
	        return this.codeNumber;
	    }
	}
	@GraphQLQuery(name = "codeName")
	public Code code;
	@GraphQLQuery(name = "description")
	public String description;
	@GraphQLQuery(name = "code")
	public int getCodeNumber() {
		return this.code.getCodeNumber();
	}
}