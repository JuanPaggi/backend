package ar.com.tandilweb.byo.backend.Presentation.GQLServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.tandilweb.byo.backend.Filters.JWT.JWTHeader;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.LoginOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.RememberEmailOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.VCardList;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO.Code;
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
	 * Servicio especializado en el control de la cuenta del usuario que accede (es decir de modificar datos de la cuenta, registrarlo, etc.)
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
			@GraphQLArgument(name = "password") String password,
			@GraphQLArgument(name = "fcm") String fcmToken) {
		try {
			if(fcmToken == null || "".equals(fcmToken)) {
				fcmToken = "no-Token";
			}
			return userAdapter.validateLogin(email, password, fcmToken);
		} catch (Exception e) {
			e.printStackTrace();
			LoginOut out = new LoginOut();
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error el servidor a fallado";
			return out;
		}
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
			@GraphQLArgument(name = "fcm") String fcmToken){
		try {
			if(fcmToken == null || "".equals(fcmToken)) {
				fcmToken = "no-Token";
			}
			return userAdapter.validateSignup(email, password, nombre, apellido, linkedin_url, summary, pic_url, fcmToken);
		} catch(Exception e) {
			e.printStackTrace();
			LoginOut out = new LoginOut();
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error, no se ha podido registrar el usuario";
			return out;
		}
	}

	@GraphQLQuery(name = "AccountService_linkedin")
	public LoginOut linkedin(
			@GraphQLArgument(name = "accessToken") String accessToken,
			@GraphQLArgument(name = "expiresOn") String expiresOn,
			@GraphQLArgument(name = "fcm") String fcmToken) {
		if(fcmToken == null || "".equals(fcmToken)) {
			fcmToken = "no-Token";
		}
		return linkedinAdapter.validateAccessToken(accessToken, fcmToken);
	}

	@GraphQLQuery(name = "AccountService_setBuscoYofrezco")
	public ResponseDTO buscoYOfrezco(
			@GraphQLArgument(name = "busco") String busco, 
			@GraphQLArgument(name = "ofrezco") String ofrezco,
			@GraphQLEnvironment ResolutionEnvironment environment
			) throws Exception {
		System.out.println(busco);
		System.out.println(ofrezco);
		JWTHeader header = JWTHeader.getHeader(environment);
		Users usuario = header.getUser();
		ResponseDTO out = new ResponseDTO();
		System.out.println(usuario.getOfrezco());
		System.out.println(usuario.getBusco());
		if (busco.isEmpty() && usuario.getBusco() != null){
			busco = usuario.getBusco();
		} else  if(busco.isEmpty() && usuario.getBusco() == null) {
			busco = "Nada en Particular";
		}
		if (ofrezco.isEmpty() && usuario.getOfrezco() != null){
			ofrezco = usuario.getOfrezco();
		} else  if(ofrezco.isEmpty() && usuario.getOfrezco() == null) {
			ofrezco = "Nada en Particular";
		}
		if(header.isTrusted()) {
			return userAdapter.setBuscoYofrezco(busco, ofrezco, usuario);
		} else {
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error datos no seteados";
		}
		return out;
	}
	

	@GraphQLQuery(name = "AccountService_rememberEmail")
	public RememberEmailOut rememberEmail(@GraphQLArgument(name = "email") String email) {
		try {
			return userAdapter.rememberEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
			RememberEmailOut out = new RememberEmailOut();
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "El email no se encuentra registrado";
			return out;
		}
	}

	@GraphQLQuery(name = "AccountService_validateRememberMailCode")
	public ResponseDTO checkCode(
			@GraphQLArgument(name = "idUser") Long idUsuario,
			@GraphQLArgument(name = "codigo") String codigo, 
			@GraphQLArgument(name = "password") String password
			) {
		ResponseDTO out = new ResponseDTO();
		try {
			out = userAdapter.validateRememberMailCode(idUsuario, codigo, password);
		} catch (Exception e) {
			out.code = ResponseDTO.Code.FORBIDDEN;
			out.description = "Codigo incorrecto";
			return out;
		}
		return out;
	}

	@GraphQLQuery(name = "AccountService_validateUnlockCode")
	public ResponseDTO validateUnlockCode(@GraphQLArgument(name = "email") String email, @GraphQLArgument(name = "codigo") String codigo) {
		ResponseDTO out = new ResponseDTO();
		try {
			out = userAdapter.validateUnlockCode(email,codigo);
		} catch (Exception e) {
			out.code = ResponseDTO.Code.FORBIDDEN;
			out.description = "Codigo incorrecto";
			return out;
		}
		return out;
	}

	@GraphQLQuery(name = "AccountService_setGeoLocation")
	public ResponseDTO setGeoLocation(
			@GraphQLArgument(name = "latitude") String lat, 
			@GraphQLArgument(name = "longitude") String lon, 
			@GraphQLEnvironment ResolutionEnvironment environment
			) throws Exception {

		JWTHeader header = JWTHeader.getHeader(environment);
		Users usuario = header.getUser();
		ResponseDTO out = new ResponseDTO();

		if(header.isTrusted()) {
			double dlat = Double.parseDouble(lat);
			double dlon = Double.parseDouble(lon);
			return userAdapter.setGeoLocation(dlat, dlon, usuario);
		} else {
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Usuario no confiable";
		}
		return out;
	}



}
