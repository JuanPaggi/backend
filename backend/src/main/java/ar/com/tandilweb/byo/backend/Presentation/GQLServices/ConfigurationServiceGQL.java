package ar.com.tandilweb.byo.backend.Presentation.GQLServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.tandilweb.byo.backend.Filters.JWT.JWTHeader;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ConfigurationDTO;
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
	 * Servicio destinado a cambiar las configuraciones.
	 * 
	 */
	private static final Logger log = LoggerFactory.getLogger(ConfigurationServiceGQL.class);
	@Autowired
	private UserAdapter userAdapter;
	@Autowired
	private ConfigurationAdapter configurationAdapter;
//	@Autowired
//	private UserRepository userRepository;
	
	@GraphQLQuery(name = "ConfigurationService_getBuscoYOfrezco")
	public VCard getBuscoYOfrezco(
			@GraphQLEnvironment ResolutionEnvironment environment){
		log.debug("Requesting ConfigurationService_getBuscoYOfrezco");
		VCard out = null;
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			if(!header.isTrusted()) throw new Exception("isn't trusteado.");
			Users usuario = header.getUser();
			out = this.userAdapter.getVCardByUser(usuario.getId_user());
		} catch (Exception e) {
			log.error("Error obteniendo configuracion", e);
		}
		log.debug("Responding ConfigurationService_getBuscoYOfrezco");
		return out;
	}
	
	
	@GraphQLQuery(name = "ConfigurationService_getInfoPerfil")
	public Users getInfoPerfil(
			@GraphQLEnvironment ResolutionEnvironment environment){
		log.debug("Requesting ConfigurationService_getInfoPerfil");
		Users out = null;
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
			out = user;
		} catch (Exception e) {
			log.error("Error obteniendo configuracion", e);
		}
		log.debug("Responding ConfigurationService_getInfoPerfil");
		return out;
	} 
	@GraphQLQuery(name = "ConfigurationService_changeByo")
	public ResponseDTO changeByo(
			@GraphQLArgument(name = "busco") String busco,
			@GraphQLArgument(name = "ofrezco") String ofrezco,
			@GraphQLEnvironment ResolutionEnvironment environment){
		log.debug("Requesting ConfigurationService_changeByo");
		ResponseDTO out = new ResponseDTO();
		JWTHeader header = JWTHeader.getHeader(environment);
		try {
			out = configurationAdapter.changeByo(busco, ofrezco, header.getUser());
		} catch(Exception e) {
			log.error("Error obteniendo configuracion", e);
			out.description = "Error, no se ha podido guardar la información";
		}
		log.debug("Responding ConfigurationService_changeByo");
		return out;
	}
	
	
	
	@GraphQLQuery(name = "ConfigurationService_updatePhoto")
	public ResponseDTO updatePhoto(
			@GraphQLArgument(name = "picture_url") String pic_url,
			@GraphQLEnvironment ResolutionEnvironment environment){
		log.debug("Requesting ConfigurationService_updatePhoto");
		ResponseDTO out = new ResponseDTO();
		JWTHeader header = JWTHeader.getHeader(environment);
		try {
			out = configurationAdapter.updatePhoto(pic_url, header.getUser());
		} catch(Exception e) {
			e.printStackTrace();
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error, no se ha podido cambiar la foto de perfil";
		}
		log.debug("Responding ConfigurationService_updatePhoto");
		return out;
	}
	

	
	@GraphQLQuery(name = "ConfigurationService_changeReceiveNotifications")
	public ResponseDTO changeReceiveNotifications(
			@GraphQLEnvironment ResolutionEnvironment environment){
		log.debug("Requesting ConfigurationService_changeReceiveNotifications");
		JWTHeader header = JWTHeader.getHeader(environment);
		ResponseDTO out = new ResponseDTO();
		Users usuario = header.getUser();
		try {
			out = configurationAdapter.changeReceiveNotifications(!usuario.getReceiveNotifications(),usuario);
		} catch(Exception e) {
			log.error("Error cambiando configuracion de notificaciones", e);
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error, no se ha podido guardar la información";
		}
		log.debug("Responding ConfigurationService_changeReceiveNotifications");
		return out;
	}
	
	@GraphQLQuery(name = "ConfigurationService_changePassword")
	public ResponseDTO changePassword(
			@GraphQLArgument(name = "password") String password,
			@GraphQLEnvironment ResolutionEnvironment environment){
		log.debug("Requesting ConfigurationService_changePassword");
		ResponseDTO out = new ResponseDTO();
		JWTHeader header = JWTHeader.getHeader(environment);
		try {
			out = configurationAdapter.changePassword(password, header.getUser());
		} catch(Exception e) {
			e.printStackTrace();
			out.description = "Error, no se ha podido cambiar el password";
		}
		log.debug("Responding ConfigurationService_changePassword");
		return out;
	}
	
	@GraphQLQuery(name = "ConfigurationService_getConfigurations")
	public ConfigurationDTO getConfigurations(
			@GraphQLEnvironment ResolutionEnvironment environment) throws Exception{
		log.debug("Requesting ConfigurationService_getConfigurations");
		ConfigurationDTO out = new ConfigurationDTO();
		JWTHeader header = JWTHeader.getHeader(environment);
		if(!header.isTrusted()) { throw new Exception("isn't trusteado."); }
		else {
			try {
				out = configurationAdapter.getConfigurations();
			} catch(Exception e) {
				e.printStackTrace();
				out.description = "Error, no se ha podido conectar al servidor";
			}
			log.debug("Responding ConfigurationService_getConfigurations");
			return out;
		}
			
		
		}
		
}
