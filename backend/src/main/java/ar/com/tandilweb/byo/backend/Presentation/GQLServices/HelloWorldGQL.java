package ar.com.tandilweb.byo.backend.Presentation.GQLServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ar.com.tandilweb.byo.backend.Presentation.dto.out.Hola;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;

@GraphQLApi
@Service
public class HelloWorldGQL {

	private static final Logger log = LoggerFactory.getLogger(HelloWorldGQL.class);

	@GraphQLQuery(name = "HelloWorldGQL_getUser")
	public Hola getUser() {
		log.debug("Llamada entrante a query USER.");
		Hola out = new Hola();
		out.setMundo("Ok");
		return out;
	}

}
