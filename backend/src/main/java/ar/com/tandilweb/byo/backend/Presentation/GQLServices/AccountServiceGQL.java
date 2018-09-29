package ar.com.tandilweb.byo.backend.Presentation.GQLServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestAttribute;

import ar.com.tandilweb.byo.backend.Model.domain.Users;
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
	public LoginOut login(
			@GraphQLArgument(name = "email") String email, 
			@GraphQLArgument(name = "password") String password) {
		log.debug("Log data: "+email+" : "+password);
		try {
			return userAdapter.validateLogin(email, password);
		} catch (Exception e) {
			e.printStackTrace();
			LoginOut out = new LoginOut();
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error en encriptación";
			return out;
		}
	}

	@GraphQLQuery(name = "AccountService_signup")
	public ResponseDTO signup(
			@GraphQLArgument(name = "email") String email, 
			@GraphQLArgument(name = "password") String password, 
			@GraphQLArgument(name = "nombre") String nombre, 
			@GraphQLArgument(name = "apellido") String apellido, 
			@GraphQLArgument(name = "linkedin_url") String linkedin_url, 
			@GraphQLArgument(name = "summary") String summary) {
		log.debug("Log data: "+email+" : "+password);
		try {
			return userAdapter.validateSignup(email, password, nombre, apellido, linkedin_url, summary);
		} catch(Exception e) {
			e.printStackTrace();
			ResponseDTO out = new ResponseDTO();
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error en encriptación";
			return out;
		}
	}

	@GraphQLQuery(name = "AccountService_linkedin")
	public LoginOut linkedin(
			@GraphQLArgument(name = "accessToken") String accessToken,
			@GraphQLArgument(name = "expiresOn") String expiresOn) {
		return linkedinAdapter.validateAccessToken(accessToken);
	}
	
	@GraphQLQuery(name = "AccountService_buscoYOfrezco")
	public void buscoYOfrezco(
			@GraphQLArgument(name = "busco") String busco, 
			@GraphQLArgument(name = "ofrezco") String ofrezco,
			@RequestAttribute("jwtUserOrigin") Users usuario,
			@RequestAttribute("jwtTrusted") boolean trusted
			) {
		if(trusted) {
			
		} else {
			
		}
	}

}
