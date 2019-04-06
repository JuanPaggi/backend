package ar.com.tandilweb.byo.backend.Transport;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.tandilweb.byo.backend.Model.domain.GeneralConfiguration;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.UserRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ConfigurationDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.LoginOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;
import ar.com.tandilweb.byo.backend.utils.CryptDES;

public class ConfigurationAdapter {

	@SuppressWarnings("unused")
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





	public ResponseDTO changeByo(String busco, String ofrezco, Users usuario) {
		LoginOut out = new LoginOut();
		usuario.setBusco(busco);
		usuario.setOfrezco(ofrezco);
		try {
			this.userRepository.changeByo(usuario);
		} catch (Exception e) {
			e.printStackTrace();
			out.code = ResponseDTO.Code.BAD_REQUEST;
			out.description = "Error de servidor";
		}
		return out;
	}
	
	public ResponseDTO changeReceiveNotifications(boolean receive_notifications, Users usuario) {
		LoginOut out = new LoginOut();
		try {
			usuario.setReceiveNotifications(receive_notifications);
			this.userRepository.changeReceiveNotifications(usuario);
		} catch (Exception e) {
			e.printStackTrace();
			out.code = ResponseDTO.Code.BAD_REQUEST;
			out.description = "Error de servidor";
		}
		return out;
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


	public ConfigurationDTO getConfigurations() {
		ConfigurationDTO out = new ConfigurationDTO();
		
		try {
			List<GeneralConfiguration> configurations = this.userRepository.getConfigurations();
			for (GeneralConfiguration gc : configurations) {
				out.add(gc.getId(), gc.getValor());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			out.code = ResponseDTO.Code.BAD_REQUEST;
			out.description = "Error de servidor";
		}
		return out;
	}
	
	
}
