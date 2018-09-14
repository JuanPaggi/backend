package ar.com.tandilweb.byo.backend.Presentation.GQLServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.tandilweb.byo.backend.Transport.LinkedInAdapter;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;

@GraphQLApi
@Service
public class AccountServiceGQL {
	
	private static final Logger log = LoggerFactory.getLogger(AccountServiceGQL.class);
	
	@Autowired
	private LinkedInAdapter linkedinAdapter;
	
	@GraphQLQuery(name = "AccountService_login")
	public void login(@GraphQLArgument(name = "user") String user, @GraphQLArgument(name = "password") String pass) {
		
	}
	
	@GraphQLQuery(name = "AccountService_signup")
	public void signup() {
		
	}
	
	@GraphQLQuery(name = "AccountService_linkedin")
	public boolean linkedin(@GraphQLArgument(name = "accessToken") String accessToken, @GraphQLArgument(name = "expiresOn") String expiresOn) {
		log.debug("AccessToken: "+accessToken);
		log.debug("ExpiresOn: "+expiresOn);
		linkedinAdapter.validateAccessToken(accessToken);
		return true;
	}

}
