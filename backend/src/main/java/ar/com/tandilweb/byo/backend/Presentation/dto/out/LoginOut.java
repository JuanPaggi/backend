package ar.com.tandilweb.byo.backend.Presentation.dto.out;

import io.leangen.graphql.annotations.GraphQLQuery;

public class LoginOut extends ResponseDTO {
	
	@GraphQLQuery(name = "userId", description = "id del usuario")
	public long userId;
	
	@GraphQLQuery(name = "first_name", description = "nombre del usuario")
	public String first_name;
	
	@GraphQLQuery(name = "last_name", description = "apellido del usuario")
	public String last_name;
	
	@GraphQLQuery(name = "is_premium", description = "el usuario es premium?")
	public boolean is_premium;
	
	@GraphQLQuery(name = "picture_url", description = "url de la imagen de perfil del usuario")
	public String picture_url;
	
	@GraphQLQuery(name = "summary", description = "descripcion del usuario")
	public String summary;
	
	@GraphQLQuery(name = "token", description = "token del usuario")
	public String token;
	
}
