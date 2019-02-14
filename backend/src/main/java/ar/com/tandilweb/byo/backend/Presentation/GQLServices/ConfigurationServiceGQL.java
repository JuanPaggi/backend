package ar.com.tandilweb.byo.backend.Presentation.GQLServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.com.tandilweb.byo.backend.Filters.JWT.JWTHeader;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.UserRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.LoginOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.VCard;
import ar.com.tandilweb.byo.backend.Transport.ConfigurationAdapter;
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
	private static final Logger log = LoggerFactory.getLogger(ConfigurationServiceGQL.class);
	@Autowired
	private UserAdapter userAdapter;
	@Autowired
	private ConfigurationAdapter configurationAdapter;
	@Autowired
	private UserRepository userRepository;
	
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
	
	
	@GraphQLQuery(name = "ConfigurationService_getInfoPerfil")
	public Users getInfoPerfil(
			@GraphQLEnvironment ResolutionEnvironment environment){
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			if(!header.isTrusted()) throw new Exception("isn't trusteado.");
			Users usuario = header.getUser();
			Users user = new Users();
			user.setEmail(usuario.getEmail());
			user.setFirstName(usuario.getFirstName());
			user.setLastName(usuario.getLast_name());
			user.setPicture_url(usuario.getPicture_url());
			user.setBusco(usuario.getBusco());
			user.setOfrezco(usuario.getOfrezco());
			user.setReceiveNotifications(usuario.getReceiveNotifications());
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	} 
	@GraphQLQuery(name = "ConfigurationService_changeByo")
	public ResponseDTO changeByo(
			@GraphQLArgument(name = "busco") String busco,
			@GraphQLArgument(name = "ofrezco") String ofrezco,
			@GraphQLEnvironment ResolutionEnvironment environment){
		JWTHeader header = JWTHeader.getHeader(environment);
		try {
			return configurationAdapter.changeByo(busco, ofrezco, header.getUser());
		} catch(Exception e) {
			e.printStackTrace();
			LoginOut out = new LoginOut();
			out.description = "Error, no se ha podido guardar la información";
			return out;
		}
	}
	
	
	
	@GraphQLQuery(name = "ConfigurationService_updatePhoto")
	public ResponseDTO updatePhoto(
			@GraphQLArgument(name = "picture_url") String pic_url,
			@GraphQLEnvironment ResolutionEnvironment environment){
		JWTHeader header = JWTHeader.getHeader(environment);
		try {
			return configurationAdapter.updatePhoto(pic_url, header.getUser());
		} catch(Exception e) {
			e.printStackTrace();
			LoginOut out = new LoginOut();
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error, no se ha podido cambiar la foto de perfil";
			return out;
		}
	}
	

	
	@GraphQLQuery(name = "ConfigurationService_changeReceiveNotifications")
	public ResponseDTO changeReceiveNotifications(
			@GraphQLEnvironment ResolutionEnvironment environment){
		JWTHeader header = JWTHeader.getHeader(environment);
		LoginOut out = new LoginOut();
		Users usuario = header.getUser();
		try {
		 return configurationAdapter.changeReceiveNotifications(!usuario.getReceiveNotifications(),usuario);
		} catch(Exception e) {
			e.printStackTrace();
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error, no se ha podido guardar la información";
			return out;
		}
	}
	
	@GraphQLQuery(name = "ConfigurationService_changePassword")
	public ResponseDTO changePassword(
			@GraphQLArgument(name = "password") String password,
			@GraphQLEnvironment ResolutionEnvironment environment){
		JWTHeader header = JWTHeader.getHeader(environment);
		try {
			return configurationAdapter.changePassword(password, header.getUser());
		} catch(Exception e) {
			e.printStackTrace();
			LoginOut out = new LoginOut();
			out.description = "Error, no se ha podido cambiar el password";
			return out;
		}
	}
}
