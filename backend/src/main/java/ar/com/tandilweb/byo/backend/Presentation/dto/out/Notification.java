package ar.com.tandilweb.byo.backend.Presentation.dto.out;

import io.leangen.graphql.annotations.GraphQLQuery;

public class Notification {
	
	public enum Typez {
		SOLICITUD_ENVIADA,
		SOLICITUD_RECIBIDA,
	}
	
	@GraphQLQuery(name = "tipo")
	public Typez tipo;
	
	@GraphQLQuery(name = "userTarget")
	public VCard userTarget;
	
	@GraphQLQuery(name = "date_emitted")
	public long date_emitted;
	

}
