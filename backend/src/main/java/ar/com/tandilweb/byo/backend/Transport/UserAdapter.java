package ar.com.tandilweb.byo.backend.Transport;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.tandilweb.byo.backend.Model.domain.GpsData;
import ar.com.tandilweb.byo.backend.Model.domain.RememberTokens;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.GpsDataRepository;
import ar.com.tandilweb.byo.backend.Model.repository.RememberTokensRepository;
import ar.com.tandilweb.byo.backend.Model.repository.UserRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.LoginOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.RememberEmailOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO.Code;
import ar.com.tandilweb.byo.backend.utils.CryptDES;
import ar.com.tandilweb.byo.backend.utils.Mailer;

public class UserAdapter {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RememberTokensRepository rememberTokenRepository;
	@Autowired
	private GpsDataRepository gpsDataRepository;

	public LoginOut validateLogin(String email, String password) throws Exception {
		System.out.println("logeando");
		LoginOut out = new LoginOut();
		Users usuario = userRepository.findByemail(email);
		if(usuario != null) {
			if (!usuario.isLocked()) {
				if (CryptDES.check(password, usuario.getPassword())) {
					// actualizamos los salts:
					UUID uuid = UUID.randomUUID();
					usuario.setSalt_jwt(uuid.toString());
					usuario.setFailedLoginAttempts(0);
					out.code = ResponseDTO.Code.OK;
					out.description = "usuario encontrado";
					// usamos el salt nuevo:
					out.token = usuario.getSalt_jwt();
					out.userId = usuario.getId_user();
					out.completoByO = usuario.isCompletoByO();
				} else {
					//Ingreso mal algun campo
					usuario.setFailedLoginAttempts(usuario.getFailedLoginAttempts()+1);
					if(usuario.getFailedLoginAttempts() >= 3) {
						String unlockCode = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 4);
						usuario.setUnLockAccountCode(unlockCode);
						Mailer.send(usuario.getEmail(), "Código para recordar desbloquear cuenta",unlockCode);
						usuario.setLocked(true);					
					}
					out.code = ResponseDTO.Code.FORBIDDEN;
					out.description = "Acceso denegado";
				}
			}else {
				//Si el usuario esta bloqueado
//				String unlockCode = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 4);
//				usuario.setUnLockAccountCode(unlockCode);
//				Mailer.send(usuario.getEmail(), "Código para recordar desbloquear cuenta",unlockCode);
				out.code = ResponseDTO.Code.BAD_REQUEST;
				out.description = "Por favor ingrese el codigo que le mandamos por mail para desbloquear";
			}
			userRepository.update(usuario);
		} else {
			out.code = ResponseDTO.Code.NOT_FOUND;
			out.description = "El usuario no existe.";
		}
		return out;
	}

	public LoginOut validateSignup(String email, String password, String nombre, String apellido, String linkedin_url,
			String summary, String picture_url) throws Exception {
		LoginOut out = new LoginOut();
		if (userRepository.findByemail(email) == null) { // El mail no se encuentra en la base de datos. Se puede
			// registrar
			Users usuario = new Users();
			usuario.setEmail(email);
			usuario.setPassword(CryptDES.getSaltedHash(password));
			usuario.setFirst_name(nombre);
			usuario.setLast_name(apellido);
			usuario.setSignup_date(new Date());
			usuario.setOfrezco("ofrezco hardcodeado");
			usuario.setBusco("Busco Hardcodeado");
			usuario.setSalt_jwt(UUID.randomUUID().toString());
			usuario.setLast_login(new Date());
			usuario.setCompletoByO(false);
			usuario.setLocked(false);
			usuario.setFailedLoginAttempts(0);
			usuario.setUnLockAccountCode("");
			if(picture_url == null) picture_url = "../../assets/imgs/512x512.png";
			usuario.setPicture_url(picture_url);
			userRepository.create(usuario);
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
		userRepository.update(usuario);
		return out;
	}

	public RememberEmailOut rememberEmail(String email) throws Exception {
		Users usuario = userRepository.findByemail(email);
		return rememberEmail(usuario);
	}

	public RememberEmailOut rememberEmail(Users usuario) throws Exception {
		RememberEmailOut out = new RememberEmailOut();
		if (usuario != null) {
			long idUsuario = usuario.getId_user();
			RememberTokens token = rememberTokenRepository.findById(idUsuario);
			String tokenRemember = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 4);
			if(token != null) {
				token.setAttempts(0);
				token.setId_user(usuario.getId_user());
				token.setUnlock_key(tokenRemember);
				token.setRequest_date(new Date());
				rememberTokenRepository.update(token);
			} else {
				token = new RememberTokens();
				token.setAttempts(0);
				token.setId_user(usuario.getId_user());
				token.setUnlock_key(tokenRemember);
				token.setRequest_date(new Date());
				rememberTokenRepository.create(token);
			}
			Mailer.send(usuario.getEmail(), "Código para recordar contraseña", "Hola mundo: "+tokenRemember);
			out.idUser = idUsuario;
			out.code = ResponseDTO.Code.OK;
			out.description = "Email encontrado";
		}else {
			out.code = ResponseDTO.Code.FORBIDDEN;
			out.description = "Acceso denegado";
		}
		return out;
	}

	public RememberEmailOut validateRememberMailCode(Long idUsuario, String codigo, String password) throws Exception {
		RememberEmailOut out = new RememberEmailOut();
		RememberTokens token = rememberTokenRepository.findById(idUsuario);
		if(token != null) {
			Users user = userRepository.findById(token.getId_user());
			if(token.getAttempts() < 3) {
				if(token.getUnlock_key() != null && codigo != null && !"".equals(token.getUnlock_key()) && codigo.equals(token.getUnlock_key())) {
					token.setUnlock_key("");
					user.setPassword(CryptDES.getSaltedHash(password));
					userRepository.update(user);
					out.code = Code.ACCEPTED;
					out.description = "Su contraseña a sido cambiada";
				} else {
					token.setAttempts(token.getAttempts()+1);
					out.code = Code.FORBIDDEN;
					out.description = "Código incorrecto, intentelo de nuevo";
				}
			} else {
				rememberEmail(user);
				out.code = Code.AUTHORIZATION_REQUIRED;
				out.description = "Ha fallado multiples veces, enviamos otro email";
			}
			rememberTokenRepository.update(token);
		}
		return out;
	}
	
	public ResponseDTO validateUnlockCode(String email, String codigoEntrada) throws Exception {
		ResponseDTO out = new ResponseDTO();
		Users usuario = userRepository.findByemail(email);
		String codigoDesbloqueo = usuario.getUnLockAccountCode();
		if (!codigoDesbloqueo.equals("") && codigoDesbloqueo.equals(codigoEntrada)) {
			usuario.setFailedLoginAttempts(0);
			usuario.setLocked(false);
			usuario.setUnLockAccountCode("");
			userRepository.update(usuario);
			out.code = ResponseDTO.Code.OK;
			out.description = "Cuenta desbloqueada";
		}else {
			out.code = Code.FORBIDDEN;
			out.description = "Codigo no valido";
		}
		return out;
	}
	
	public ResponseDTO setGeoLocation(double latitude, double longitude, Users usuario) throws Exception {
		ResponseDTO out = new ResponseDTO();
		GpsData gps = gpsDataRepository.getOrCreate(latitude, longitude);
		gpsDataRepository.assocPositionWithUser(gps.getId_gps_record(), usuario.getId_user());
		out.code = ResponseDTO.Code.OK;
		out.description = "latitud: "+latitude+" longitud: "+longitude+ " usuarioid: "+usuario.getId_user();
		return out;
	}
}
