package ar.com.tandilweb.byo.backend.Presentation.dto.out;

import io.leangen.graphql.annotations.GraphQLQuery;

public class ChatsDTO {

	@GraphQLQuery(name = "id_usuario")
	public long id_usuario;
	
	@GraphQLQuery(name = "nombre")
	public String nombre;
	
	@GraphQLQuery(name = "picture")
	public String picture;
	
	@GraphQLQuery(name = "mensaje")
	public String mensaje;
	
	@GraphQLQuery(name = "fecha")
	public String fecha;
}
