package ar.com.tandilweb.byo.backend.Transport;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.tandilweb.byo.backend.Gateway.LinkedInConsumer;
import ar.com.tandilweb.byo.backend.Gateway.dto.LinkedInProfile;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.UserRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.LoginOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;

public class LinkedInAdapter {
	
	@Autowired
	LinkedInConsumer linkedInConsumer;
	
	@Autowired
	UserRepository userRepository;
	
	public LoginOut validateAccessToken(String accessToken) {
		LoginOut out = new LoginOut();
		LinkedInProfile response = linkedInConsumer.checkAccessToken(accessToken);
		UUID uuid = UUID.randomUUID();
		if(response != null) {
			Users usuario = userRepository.findBylinkedinId(response.getId());
			if(usuario != null) {
				usuario.setSalt_jwt(uuid.toString());
				userRepository.update(usuario);
				out.code = ResponseDTO.Code.OK;
				out.description = "";
				out.token = uuid.toString();
				out.completoByO = usuario.isCompletoByO();
				out.userId = usuario.getId_user();
			} else {
				Users user = new Users();
				user.setEmail(response.getEmailAddress());
				user.setFirst_name(response.getFirstName());
				user.setIsPremium(false);
				user.setLast_login(new Date());
				user.setLast_name(response.getLastName());
				user.setLinkedin_id(response.getId());
				user.setPicture_url(response.getPictureUrl());
				user.setSignup_date(new Date());
				user.setPassword("password hardcodeado");
				user.setOfrezco("ofrezco hardcodeado");
				user.setBusco("Busco Hardcodeado");
				user.setCompletoByO(false);
				user.setSalt_jwt(uuid.toString());
				user.setLocked(false);
				user.setFailedLoginAttempts(0);
				user.setUnLockAccountCode("");
				user = userRepository.create(user);
				
				out.userId = user.getId_user();
				out.first_name = user.getFirst_name();
				out.last_name = user.getLast_name();
				out.is_premium = user.isPremium();
				out.picture_url = "imagenURL";
				// enviamos el token.
				out.token = user.getSalt_jwt();
				out.completoByO = user.isCompletoByO();
				
				out.code = ResponseDTO.Code.CREATED;
				out.description = "Usuario Creado";
				out.token = uuid.toString();
				out.userId = user.getId_user();
			}
		} else {
			out.code = ResponseDTO.Code.FORBIDDEN;
			out.description = "Token inválido";
		}
		return out;
	}

}
