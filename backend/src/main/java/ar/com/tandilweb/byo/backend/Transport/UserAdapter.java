package ar.com.tandilweb.byo.backend.Transport;

import java.util.UUID;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.tandilweb.byo.backend.Model.domain.RememberTokens;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.RememberTokensRepository;
import ar.com.tandilweb.byo.backend.Model.repository.UserRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.LoginOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;
import ar.com.tandilweb.byo.backend.utils.CryptDES;

public class UserAdapter {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RememberTokensRepository rememberTokenRepository;
	
	public LoginOut validateLogin(String email, String password) throws Exception {
		LoginOut out = new LoginOut();
		Users usuario = userRepository.findByemail(email);

		// fin actualización
		if (usuario != null && CryptDES.check(password, usuario.getPassword())) {
			// actualizamos los salts:
			UUID uuid = UUID.randomUUID();
			usuario.setSalt_jwt(uuid.toString());
			userRepository.save(usuario);
			
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
	
	public ResponseDTO setBuscoYofrezco(String busco, String ofrezco, Users usuario) throws Exception {
		ResponseDTO out = new ResponseDTO();
		out.code = ResponseDTO.Code.OK;
		out.description = "Busco y ofrezco seteados";
		usuario.setBusco(busco);
		usuario.setOfrezco(ofrezco);
		usuario.setCompletoByO(true);
		userRepository.save(usuario);
		return out;
	}
	
	public ResponseDTO rememberEmail(String email) throws Exception {
		ResponseDTO out = new ResponseDTO();
		Users usuario = userRepository.findByemail(email);
		if (usuario != null) {
			RememberTokens token = rememberTokenRepository.findById(usuario.getId_user());
			System.out.println(token+" ");
		}else {
			out.code = ResponseDTO.Code.FORBIDDEN;
			out.description = "Acceso denegado";
		}
		return out;
	}
}
