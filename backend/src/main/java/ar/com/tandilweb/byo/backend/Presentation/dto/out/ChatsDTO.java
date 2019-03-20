package ar.com.tandilweb.byo.backend.Presentation.dto.out;

import io.leangen.graphql.annotations.GraphQLQuery;

public class ChatsDTO {

	@GraphQLQuery(name = "id_usuario")
	public long id_usuario;
	
	@GraphQLQuery(name = "vcard")
	public VCard vcard;
	
	@GraphQLQuery(name = "mensaje")
	public String mensaje;
	
	@GraphQLQuery(name = "fecha")
	public String fecha;
}
