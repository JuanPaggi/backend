package ar.com.tandilweb.byo.backend.Presentation.GQLServices;

import org.springframework.stereotype.Service;

import ar.com.tandilweb.byo.backend.Presentation.dto.out.Hola;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;

@GraphQLApi
@Service
public class HelloWorldGQL {
	
	 @GraphQLQuery(name = "user")
	 public Hola getUser() {
	      Hola out = new Hola();
	      out.mundo = "Ok";
	      return out;
	 }

}
