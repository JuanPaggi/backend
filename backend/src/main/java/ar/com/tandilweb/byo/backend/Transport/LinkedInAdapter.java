package ar.com.tandilweb.byo.backend.Transport;

import java.util.Date;

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
		if(response != null) {
			Users usuario = userRepository.findByLinkedin_id(response.getId());
			if(usuario != null) {
				String token = "456456456456";
				out.code = ResponseDTO.Code.OK;
				out.description = "";
				out.token = token;
				out.userId = usuario.getId_user();
			} else {
				Users user = new Users();
				user.setEmail(response.getEmailAddress());
				user.setFirst_name(response.getFirstName());
				user.setIs_premium(false);
				user.setLast_login(new Date());
				user.setLast_name(response.getLastName());
				user.setLinkedin_id(response.getId());
				user.setPicture_url(response.getPictureUrl());
				user.setSignup_date(new Date());
				String token = "456456456456";
				user.setSalt_jwt(token);
				user = userRepository.save(user);
				out.code = ResponseDTO.Code.CREATED;
				out.description = "Usuario Creado";
				out.token = token;
				out.userId = user.getId_user();
			}
		} else {
			out.code = ResponseDTO.Code.FORBIDDEN;
			out.description = "Token inválido";
		}
		return out;
	}

}
