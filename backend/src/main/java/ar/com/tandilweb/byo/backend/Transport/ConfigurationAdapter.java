package ar.com.tandilweb.byo.backend.Transport;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.tandilweb.byo.backend.Model.domain.Friendships;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.UserRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.LoginOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.VCard;
import ar.com.tandilweb.byo.backend.utils.CryptDES;

public class ConfigurationAdapter {

	private static final Logger log = LoggerFactory.getLogger(ConfigurationAdapter.class);
	
	@Autowired
	private UserRepository userRepository;
	
	
	public String getMail(Users me) {


		return me.getEmail();
	}


	public ResponseDTO updatePhoto(String pic_url, Users user) {
		user.setPicture_url(pic_url);
			this.userRepository.updatePhoto(user);
		
		
		
		return null;
	}


	public ResponseDTO changePassword(String password, Users usuario) {
		LoginOut out = new LoginOut();
		try {
			usuario.setPassword(CryptDES.getSaltedHash(password));
			this.userRepository.changePassword(usuario);
		} catch (Exception e) {
			e.printStackTrace();
			out.code = ResponseDTO.Code.BAD_REQUEST;
			out.description = "Error de servidor";
		}
		return out;
	}
	
	
}
