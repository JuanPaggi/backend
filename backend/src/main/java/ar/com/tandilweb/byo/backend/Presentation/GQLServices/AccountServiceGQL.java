package ar.com.tandilweb.byo.backend.Presentation.GQLServices;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.tandilweb.byo.backend.Filters.JWT.JWTHeader;
import ar.com.tandilweb.byo.backend.Model.domain.Stands;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.LoginOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.RememberEmailOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;
import ar.com.tandilweb.byo.backend.Transport.LinkedInAdapter;
import ar.com.tandilweb.byo.backend.Transport.UserAdapter;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;

@GraphQLApi
@Service
public class AccountServiceGQL {
	
	/**
	 * Servicio especial6izado en el control de la cuenta del usuario que accede (es decir de modificar datos de la cuenta, registrarlo, etc.)
	 * Pero referentes al usuario actual.
	 */

	private static final Logger log = LoggerFactory.getLogger(AccountServiceGQL.class);

	@Autowired
	private LinkedInAdapter linkedinAdapter;
	
	@Autowired
	private UserAdapter userAdapter;

	@GraphQLQuery(name = "AccountService_login")
	public LoginOut login(
			@GraphQLArgument(name = "email") String email, 
			@GraphQLArgument(name = "password") String password
			//@GraphQLArgument(name = "fcm") String fcmToken // fcm se cambia de servicio
			) {
		LoginOut out;
		try {
			log.debug("Requesting AccountService_login");
			out = userAdapter.validateLogin(email, password);
		} catch (Exception e) {
			e.printStackTrace();
			out = new LoginOut();
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error el servidor a fallado";
		}
		log.debug("Responding AccountService_login");
		return out;
	}

	@GraphQLQuery(name = "AccountService_signup")
	public LoginOut signup(
			@GraphQLArgument(name = "email") String email, 
			@GraphQLArgument(name = "password") String password, 
			@GraphQLArgument(name = "nombre") String nombre, 
			@GraphQLArgument(name = "apellido") String apellido, 
			@GraphQLArgument(name = "linkedin_url") String linkedin_url, 
			@GraphQLArgument(name = "summary") String summary,
			@GraphQLArgument(name = "picture_url") String pic_url,
			@GraphQLArgument(name = "fcm") String fcmToken,
			@GraphQLArgument(name = "receive_notifications") Boolean receive_notifications
			){
		log.debug("Requesting AccountService_signup");
		//boolean receive_notifications = false;
		LoginOut out;
		try {
			if(fcmToken == null || "".equals(fcmToken)) {
				fcmToken = "no-Token";
			}
			out = userAdapter.validateSignup(email, password, nombre, apellido, linkedin_url, summary, pic_url,receive_notifications, fcmToken); 
			
		} catch(Exception e) {
			e.printStackTrace();
			out = new LoginOut();
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error, no se ha podido registrar el usuario";
		}
		log.debug("Responding AccountService_signup");
		return out;
	}

	@GraphQLQuery(name = "AccountService_linkedin")
	public LoginOut linkedin(
			@GraphQLArgument(name = "accessToken") String accessToken,
			@GraphQLArgument(name = "expiresOn") String expiresOn) {
		log.debug("Requesting AccountService_linkedin");
		LoginOut out = linkedinAdapter.validateAccessToken(accessToken);
		log.debug("Responding AccountService_linkedin");
		return out;
	} 
	
	@GraphQLQuery(name = "AccountService_linkedinV2")
	public LoginOut linkedinV2(
			@GraphQLArgument(name = "codeScope") String codeScope) {
		log.debug("Requesting AccountService_linkedinV2");
		String accessToken = linkedinAdapter.getAccessToken(codeScope);
		LoginOut out = linkedinAdapter.validateAccessToken(accessToken); 
		log.debug("Responding AccountService_linkedinV2");
		return out;
		//return linkedinAdapter.validateAccessToken(accessToken, fcmToken);
	}
	
	@GraphQLQuery(name = "AccountService_updateFCMToken")
	public ResponseDTO updateFCMToken(
			@GraphQLArgument(name = "token") String fcm,
			@GraphQLEnvironment ResolutionEnvironment environment
			) {
		log.debug("Requesting AccountService_updateFCMToken");
		JWTHeader header = JWTHeader.getHeader(environment);
		Users usuario = header.getUser();
		ResponseDTO out = new ResponseDTO();
		userAdapter.updateFCMToken(usuario, fcm);
		out.code = ResponseDTO.Code.ACCEPTED;
		out.description = "OK";
		log.debug("Responding AccountService_updateFCMToken");
		return out;
	}

	@GraphQLQuery(name = "AccountService_setBuscoYofrezco")
	public ResponseDTO buscoYOfrezco(
			@GraphQLArgument(name = "busco") String busco, 
			@GraphQLArgument(name = "ofrezco") String ofrezco,
			@GraphQLEnvironment ResolutionEnvironment environment
			) throws Exception {
		log.debug("Requesting AccountService_setBuscoYofrezco");
		JWTHeader header = JWTHeader.getHeader(environment);
		Users usuario = header.getUser();
		ResponseDTO out = new ResponseDTO();
		boolean completoByO = true;
		boolean comp = false;
		if (busco.isEmpty()){
			busco = usuario.getBusco();
			comp = true;

		}   if(comp && usuario.getBusco().equals("Nada en Particular")) {
			completoByO = false;
			busco = "Nada en Particular";
			
		}
			comp = false;
		if (ofrezco.equals("") ){
			ofrezco = usuario.getOfrezco();
			comp = true;
		}   if(comp && usuario.getOfrezco().equals("Nada en Particular")) {
			completoByO = false;
			ofrezco = "Nada en Particular";
			
		}
		comp = false;
		if(header.isTrusted()) {
			out = userAdapter.setBuscoYofrezco(busco, ofrezco, completoByO, usuario);
		} else {
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error datos no seteados";
		}
		log.debug("Responding AccountService_setBuscoYofrezco");
		return out;
	}
	

	@GraphQLQuery(name = "AccountService_rememberEmail")
	public RememberEmailOut rememberEmail(@GraphQLArgument(name = "email") String email) {
		log.debug("Requesting AccountService_rememberEmail");
		RememberEmailOut out;
		try {
			out = userAdapter.rememberEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
			out = new RememberEmailOut();
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "El email no se encuentra registrado";
		}
		log.debug("Responding AccountService_rememberEmail");
		return out;
	}

	@GraphQLQuery(name = "AccountService_validateRememberMailCode")
	public ResponseDTO checkCode(
			@GraphQLArgument(name = "idUser") Long idUsuario,
			@GraphQLArgument(name = "codigo") String codigo, 
			@GraphQLArgument(name = "password") String password
			) {
		log.debug("Requesting AccountService_validateRememberMailCode");
		ResponseDTO out = new ResponseDTO();
		try {
			out = userAdapter.validateRememberMailCode(idUsuario, codigo, password);
		} catch (Exception e) {
			out.code = ResponseDTO.Code.FORBIDDEN;
			out.description = "Codigo incorrecto";
		}
		log.debug("Responding AccountService_validateRememberMailCode");
		return out;
	}

	@GraphQLQuery(name = "AccountService_validateUnlockCode")
	public ResponseDTO validateUnlockCode(@GraphQLArgument(name = "email") String email, @GraphQLArgument(name = "codigo") String codigo) {
		log.debug("Requesting AccountService_validateUnlockCode");
		ResponseDTO out = new ResponseDTO();
		try {
			out = userAdapter.validateUnlockCode(email,codigo);
		} catch (Exception e) {
			out.code = ResponseDTO.Code.FORBIDDEN;
			out.description = "Codigo incorrecto";
		}
		log.debug("Responding AccountService_validateUnlockCode");
		return out;
	}

	@GraphQLQuery(name = "AccountService_setGeoLocation")
	public ResponseDTO setGeoLocation(
			@GraphQLArgument(name = "latitude") String lat, 
			@GraphQLArgument(name = "longitude") String lon, 
			@GraphQLEnvironment ResolutionEnvironment environment
			) throws Exception {
		log.debug("Requesting AccountService_setGeoLocation");
		JWTHeader header = JWTHeader.getHeader(environment);
		Users usuario = header.getUser();
		ResponseDTO out = new ResponseDTO();

		if(header.isTrusted()) {
			double dlat = Double.parseDouble(lat);
			double dlon = Double.parseDouble(lon);
			out = userAdapter.setGeoLocation(dlat, dlon, usuario);
		} else {
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Usuario no confiable";
		}
		log.debug("Responding AccountService_setGeoLocation");
		return out;
	}
	
	@GraphQLQuery(name = "AccountService_getPremiumStatus")
	public boolean getStands(
			@GraphQLEnvironment ResolutionEnvironment environment){

		
		
		boolean out = false;
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			if(!header.isTrusted()) throw new Exception("isn't trusteado.");
			out= header.getUser().isPremium();
		} catch (Exception e) {
			log.error("Error al chequear el estado Premium", e);
		}

		return out;
	} 

}
