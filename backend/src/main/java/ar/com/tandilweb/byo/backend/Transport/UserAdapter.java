package ar.com.tandilweb.byo.backend.Transport;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.tandilweb.byo.backend.Gateway.dto.LinkedInProfile;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.UserRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.LoginOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;

public class UserAdapter {

	@Autowired
	UserRepository userRepository;

	public ResponseDTO validateLogin(String email, String password) {
		ResponseDTO out = new ResponseDTO();
		Users usuario = userRepository.findByemail(email);
		if(usuario != null) {
			out.code = ResponseDTO.Code.OK;
			out.description = "usuario encontrado";
		} else {
			out.code = ResponseDTO.Code.NOT_FOUND;
			out.description = "Usuario no encontrado";
		}
		return out;
	}
}
