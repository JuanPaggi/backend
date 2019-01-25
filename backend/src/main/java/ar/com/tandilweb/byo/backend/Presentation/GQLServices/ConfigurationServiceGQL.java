package ar.com.tandilweb.byo.backend.Presentation.GQLServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.com.tandilweb.byo.backend.Filters.JWT.JWTHeader;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.LoginOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.VCard;
import ar.com.tandilweb.byo.backend.Transport.UserAdapter;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;

@GraphQLApi
@Service
public class ConfigurationServiceGQL {

	/**
	 * Servicio destinado a cambiar las configuraciones del usuario.
	 * 
	 */
	@Autowired
	private UserAdapter userAdapter;
	
	@GraphQLQuery(name = "ConfigurationService_getBuscoYOfrezco")
	public VCard getBuscoYOfrezco(
			@GraphQLEnvironment ResolutionEnvironment environment){
		
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			if(!header.isTrusted()) throw new Exception("isn't trusteado.");
			Users usuario = header.getUser();
			return this.userAdapter.getVCardByUser(usuario.getId_user());
		} catch (Exception e) {
			e.printStackTrace();
			VCard out = null;
			
			return out;
		}
	}
	
}
