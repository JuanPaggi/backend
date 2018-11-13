package ar.com.tandilweb.byo.backend.Transport;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.UserRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;

public class FriendshipsAdapter {
		
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRepository FriendShipRepository;
	
	public ResponseDTO validateFriendshipRequest(long idreq, long idtar) {
		ResponseDTO out = new ResponseDTO();
		Users requester = userRepository.findById(idreq);
		Users target = userRepository.findById(idtar);
		if(requester != null && target != null) {
			
		}
	}
}
