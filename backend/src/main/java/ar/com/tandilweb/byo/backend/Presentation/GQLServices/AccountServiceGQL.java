package ar.com.tandilweb.byo.backend.Presentation.GQLServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;

@GraphQLApi
@Service
public class AccountServiceGQL {
	
	//private static final Logger log = LoggerFactory.getLogger(AccountServiceGQL.class);
	
	@GraphQLQuery(name = "AccountService_login")
	public void login(@GraphQLArgument(name = "user") String usuario, @GraphQLArgument(name = "password") String pass) {
		
	}
	
	@GraphQLQuery(name = "AccountService_signup")
	public void signup(@GraphQLArgument(name = "user") String usuario, @GraphQLArgument(name = "password") String pass) {
		
	}
	
	@GraphQLQuery(name = "AccountService_linkedin")
	public void linkedin(@GraphQLArgument(name = "user") String usuario) {
		
	}

}
