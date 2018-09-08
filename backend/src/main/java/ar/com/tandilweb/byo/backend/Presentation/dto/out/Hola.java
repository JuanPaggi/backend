package ar.com.tandilweb.byo.backend.Presentation.dto.out;

import io.leangen.graphql.annotations.GraphQLQuery;

public class Hola {
	private String mundo;

	@GraphQLQuery(name = "mundo", description = "probando acceso a propiedad")
	public String getMundo() {
		return mundo;
	}

	public void setMundo(String mundo) {
		this.mundo = mundo;
	}
}
