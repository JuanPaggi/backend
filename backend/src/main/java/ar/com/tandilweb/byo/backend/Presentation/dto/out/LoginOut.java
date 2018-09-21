package ar.com.tandilweb.byo.backend.Presentation.dto.out;

import io.leangen.graphql.annotations.GraphQLQuery;

public class LoginOut extends ResponseDTO {
	
	@GraphQLQuery(name = "userId", description = "id del usuario")
	public long userId;
	
	@GraphQLQuery(name = "token", description = "token del usuario")
	public String token;
	
}
