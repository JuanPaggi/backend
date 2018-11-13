package ar.com.tandilweb.byo.backend.Transport;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.tandilweb.byo.backend.Model.domain.Friendships;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.FriendshipsRepository;
import ar.com.tandilweb.byo.backend.Model.repository.UserRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO.Code;

public class FriendshipsAdapter {
		
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FriendshipsRepository friendShipRepository;
	
	public ResponseDTO validateFriendshipRequest(long idreq, long idtar) {
		ResponseDTO out = new ResponseDTO();
		Users requester = userRepository.findById(idreq);
		Users target = userRepository.findById(idtar);
		if(requester != null && target != null) {
			Friendships fs = new Friendships();
			fs.setDate_emitted(new Date());
			fs.setId_user_requester(idreq);
			fs.setId_user_target(idtar);
			Friendships record = friendShipRepository.create(fs);
			if (record != null) {
				out.code = Code.CREATED;
				out.description = "Asociación exitosa";
			} else {
				out.code = Code.INTERNAL_SERVER_ERROR;
				out.description = "No se pudo crear el registro";
			}
		} else {
			out.code = Code.BAD_REQUEST;
			out.description = "Uno de los usuarios no existe";
		}
		return out;
	}
}
