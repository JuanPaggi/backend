package ar.com.tandilweb.byo.backend.Presentation.GQLServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.tandilweb.byo.backend.Presentation.dto.out.LoginOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;
import ar.com.tandilweb.byo.backend.Transport.LinkedInAdapter;
import ar.com.tandilweb.byo.backend.Transport.UserAdapter;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;

@GraphQLApi
@Service
public class AccountServiceGQL {

	private static final Logger log = LoggerFactory.getLogger(AccountServiceGQL.class);

	@Autowired
	private LinkedInAdapter linkedinAdapter;
	@Autowired
	private UserAdapter userAdapter;

	@GraphQLQuery(name = "AccountService_login")
	public LoginOut login(@GraphQLArgument(name = "email") String email, @GraphQLArgument(name = "password") String password) {
		log.debug("Log data: "+email+" : "+password);
		return userAdapter.validateLogin(email, password);
	}

	@GraphQLQuery(name = "AccountService_signup")
	public ResponseDTO signup(@GraphQLArgument(name = "email") String email, @GraphQLArgument(name = "password") String password, @GraphQLArgument(name = "nombre") String nombre, @GraphQLArgument(name = "apellido") String apellido, @GraphQLArgument(name = "linkedin_url") String linkedin_url, @GraphQLArgument(name = "summary") String summary) {
		return userAdapter.validateSignup(email, password, nombre, apellido, linkedin_url,summary);
	}

	@GraphQLQuery(name = "AccountService_linkedin")
	public LoginOut linkedin(@GraphQLArgument(name = "accessToken") String accessToken, @GraphQLArgument(name = "expiresOn") String expiresOn) {
		//log.debug("AccessToken: "+accessToken);
		//log.debug("ExpiresOn: "+expiresOn);
		return linkedinAdapter.validateAccessToken(accessToken);
	}

}
