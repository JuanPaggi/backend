package ar.com.tandilweb.byo.backend.Transport;

import java.util.UUID;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.UserRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.LoginOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;
import ar.com.tandilweb.byo.backend.utils.CryptDES;

public class UserAdapter {

	@Autowired
	UserRepository userRepository;

	public LoginOut validateLogin(String email, String password) throws Exception {
		LoginOut out = new LoginOut();
		Users usuario = userRepository.findByemail(email);
		UUID uuid = UUID.randomUUID();
		// actualizamos los salts:
		usuario.setSalt_jwt(uuid.toString());
		userRepository.save(usuario);
		// fin actualización
		if (usuario != null && CryptDES.check(password, usuario.getPassword())) {
			out.code = ResponseDTO.Code.OK;
			out.description = "usuario encontrado";
			// usamos el salt nuevo:
			out.token = usuario.getSalt_jwt();
			out.userId = usuario.getId_user();
			out.completoByO = usuario.isCompletoByO();
		} else {
			out.code = ResponseDTO.Code.FORBIDDEN;
			out.description = "Acceso denegado";
		}
		return out;
	}

	public LoginOut validateSignup(String email, String password, String nombre, String apellido, String linkedin_url,
			String summary) throws Exception {
		LoginOut out = new LoginOut();
		if (userRepository.findByemail(email) == null) { // El mail no se encuentra en la base de datos. Se puede
															// registrar
			Users usuario = new Users();
			usuario.setEmail(email);
			usuario.setPassword(CryptDES.getSaltedHash(password));
			usuario.setFirst_name(nombre);
			usuario.setLast_name(apellido);
			usuario.setSignup_date(new Date());
			usuario.setPicture_url("urlHardcodeada");
			usuario.setOfrezco("ofrezco hardcodeado");
			usuario.setBusco("Busco Hardcodeado");
			usuario.setSalt_jwt(UUID.randomUUID().toString());
			usuario.setLast_login(new Date());
			usuario.setCompletoByO(false);
			userRepository.save(usuario);
			out.userId = usuario.getId_user();
			out.code = ResponseDTO.Code.CREATED;
			out.description = "Usuario Creado";
			out.first_name = usuario.getFirst_name();
			out.last_name = usuario.getLast_name();
			out.is_premium = usuario.isPremium();
			out.picture_url = "imagenURL";
			// enviamos el token.
			out.token = usuario.getSalt_jwt();
			out.completoByO = usuario.isCompletoByO();
		} else {
			out.code = ResponseDTO.Code.BAD_REQUEST;
			out.description = "Ya existe un usuario con ese mail";
		}
		return out;
	}
}
