package ar.com.tandilweb.byo.backend.Presentation.dto.out;

import java.util.Date;

import io.leangen.graphql.annotations.GraphQLQuery;

public class RememberEmailOut extends ResponseDTO {
	@GraphQLQuery(name = "idUser")
	public Long idUser;
	@GraphQLQuery(name = "attempts")
	public int attempts;
	@GraphQLQuery(name = "expDate")
	public Date expDate;
}
