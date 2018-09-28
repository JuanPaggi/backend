package ar.com.tandilweb.byo.backend.Transport;

import java.util.UUID;
import java.util.Date;

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
	
	public LoginOut validateSignup(String email, String password, String nombre, String apellido, String linkedin_url, String summary) {
		LoginOut out = new LoginOut();
		if (userRepository.findByemail(email) == null) { //El mail no se encuentra en la base de datos. Se puede registrar
			Users usuario = new Users();
			usuario.setEmail(email);
			usuario.setPassword(password);
			usuario.setFirst_name(nombre);
			usuario.setLast_name(apellido);
			usuario.setSignup_date(new Date());
			usuario.setPicture_url("urlHardcodeada");
			usuario.setOfrezco("ofrezco hardcodeado");
			usuario.setBusco("Busco Hardcodeado");
			usuario.setSalt_jwt("salt harcodeado");
			usuario.setLast_login(new Date());
			userRepository.save(usuario);
			out.userId = usuario.getId_user();
			out.code = ResponseDTO.Code.CREATED;
			out.description = "Usuario Creado";
			out.first_name = nombre;
			out.last_name = apellido;
			out.is_premium = false;
			out.picture_url = "imagenURL";
			
			
		}else {
			out.code = ResponseDTO.Code.BAD_REQUEST;
			out.description = "Ya existe un usuario con ese mail";
		}
		return out;
	}
}
