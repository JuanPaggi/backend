package ar.com.tandilweb.byo.backend.Transport;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.UserRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.LoginOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;

public class UserAdapter {

	@Autowired
	UserRepository userRepository;

	public LoginOut validateLogin(String email, String password) {
		LoginOut out = new LoginOut();
		Users usuario = userRepository.findByemail(email);
		UUID uuid = UUID.randomUUID();
		if(usuario != null) {
			out.code = ResponseDTO.Code.OK;
			out.description = "usuario encontrado";
			out.token = uuid.toString();
			out.userId = usuario.getId_user();
		} else {
			out.code = ResponseDTO.Code.NOT_FOUND;
			out.description = "Usuario no encontrado";
		}
		return out;
	}
}
