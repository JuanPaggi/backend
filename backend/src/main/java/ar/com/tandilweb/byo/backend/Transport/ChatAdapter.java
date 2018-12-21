package ar.com.tandilweb.byo.backend.Transport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.tandilweb.byo.backend.Model.domain.Mensajes;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.MensajesRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ListMessageDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO.Code;

public class ChatAdapter {
	
	@Autowired
	private MensajesRepository mensajesRepository;
	
	public ListMessageDTO getChatWith(Users me, long targetID) {
		
		ListMessageDTO out = new ListMessageDTO();
		List<Mensajes> mensajes = mensajesRepository.getChatWith(me.getId_user(), targetID);
		if(mensajes != null) {
			out.listaMensajes = mensajes;
			out.code = Code.OK;
		} else {
			out.code = Code.INTERNAL_SERVER_ERROR;
			out.description = "ERROR al obtener mensajes";
		}
		
		return out;
	}

}
