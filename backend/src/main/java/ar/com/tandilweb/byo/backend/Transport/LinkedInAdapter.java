package ar.com.tandilweb.byo.backend.Transport;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.tandilweb.byo.backend.Gateway.LinkedInConsumer;
import ar.com.tandilweb.byo.backend.Gateway.dto.LinkedInProfile;
import ar.com.tandilweb.byo.backend.Gateway.dto.linkedIn.Positions;
import ar.com.tandilweb.byo.backend.Model.domain.Countries;
import ar.com.tandilweb.byo.backend.Model.domain.Profile;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.ProfileRepository;
import ar.com.tandilweb.byo.backend.Model.repository.UserRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.LoginOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;

public class LinkedInAdapter {
	
	@Autowired
	LinkedInConsumer linkedInConsumer;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProfileRepository profileRepository;
	
	public LoginOut validateAccessToken(String accessToken, String fcmToken) {
		LoginOut out = new LoginOut();
		LinkedInProfile response = linkedInConsumer.checkAccessToken(accessToken);
		UUID uuid = UUID.randomUUID();
		if(response != null) {
			Users usuario = userRepository.findBylinkedinId(response.getId());	
			System.out.println("linkedin id: "+usuario==null);
			if(usuario != null) {
				//Este usuario ya accedio por linkedin previamente
				usuario.setSalt_jwt(uuid.toString());
				usuario.setFcmToken(fcmToken);
				userRepository.update(usuario);
				updateProfile(usuario.getId_user(), response);
				out.code = ResponseDTO.Code.OK;
				out.description = "";
				out.token = uuid.toString();
				out.completoByO = usuario.isCompletoByO();
				out.userId = usuario.getId_user();
			} else { 
				//Si no se encontro usuario con el id de linkedin nos fijamos
				//que no exista un usuario registrado con el mismo mail que el de linkedin
				usuario = userRepository.findByemail(response.getEmailAddress());
				if(usuario != null) {
					usuario.setLinkedin_id(response.getId());
					usuario.setSalt_jwt(uuid.toString());
					usuario.setFcmToken(fcmToken);
					usuario.setPicture_url(response.getPictureUrl());
					createProfile(usuario.getId_user(), response);
					userRepository.update(usuario);			
				}else {
					//Se crea el nuevo usuario
					Users user = createUser(response,fcmToken,uuid);						
					//Creamos el perfil
					createProfile(user.getId_user(), response);					
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
			}
		} else {
			out.code = ResponseDTO.Code.FORBIDDEN;
			out.description = "Token inválido";
		}
		return out;
	}
	
	private void createProfile(long userId, LinkedInProfile lp) {
		Profile userProfile = new Profile();
		userProfile = setProfileData(userProfile, lp);
		profileRepository.create(userProfile);
	}
		
	private void updateProfile(long userId, LinkedInProfile lp){
		Profile userProfile = profileRepository.findById(userId);
		if (userProfile != null) {
			userProfile = setProfileData(userProfile, lp);
			profileRepository.update(userProfile);
		} else { // si el usuario no tenía perfil lo creamos.
			this.createProfile(userId, lp);
		}
	}
	
	private Profile setProfileData(Profile userProfile, LinkedInProfile lp) {
		userProfile.setHeadline(lp.getHeadline());
		userProfile.setIndustry(lp.getIndustry());
		userProfile.setLocation(lp.getLocation().getName());
		userProfile.setLinkedin_url(lp.getPublicProfileUrl());
		String summary = lp.getSummary();
		if(summary.length() > 250) {
			summary = summary.substring(0, 250);
		}
		userProfile.setSummary(summary);
		userProfile.setCompany_name(lp.getPositions().getValues().get(0).getCompany().getName());
		userProfile.setCurrent_position(lp.getPositions().getValues().get(0).getTitle());
		return userProfile;
	}
	
	private Users createUser(LinkedInProfile lp, String fcmToken, UUID uuid) {
		Users user = new Users();
		user.setEmail(lp.getEmailAddress());
		user.setFirst_name(lp.getFirstName());
		user.setIsPremium(false);
		user.setLast_login(new Date());
		user.setLast_name(lp.getLastName());
		user.setLinkedin_id(lp.getId());
		user.setPicture_url(lp.getPictureUrl());
		user.setSignup_date(new Date());
		user.setPassword("password hardcodeado");
		user.setOfrezco("ofrezco hardcodeado");
		user.setBusco("Busco Hardcodeado");
		user.setCompletoByO(false);
		user.setSalt_jwt(uuid.toString());
		user.setLocked(false);
		user.setFailedLoginAttempts(0);
		user.setUnLockAccountCode("");
		user.setFcmToken(fcmToken);
		return userRepository.create(user);
	}
}
