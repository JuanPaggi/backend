package ar.com.tandilweb.byo.backend.Transport;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.tandilweb.byo.backend.Model.domain.Chats;
import ar.com.tandilweb.byo.backend.Model.domain.Mensajes;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.ChatRepository;
import ar.com.tandilweb.byo.backend.Model.repository.MensajesRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ListMessageDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.MensajeDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO.Code;

public class ChatAdapter {
	
	@Autowired
	private MensajesRepository mensajesRepository;
	
	@Autowired
	private ChatRepository chatRepository;
	
	public ListMessageDTO getChatWith(Users me, long targetID) {
		
		ListMessageDTO out = new ListMessageDTO();
		List<Mensajes> mensajes = mensajesRepository.getChatWith(me.getId_user(), targetID);
		if(mensajes != null) {
			out.listaMensajes = new ArrayList<MensajeDTO>();
			for(Mensajes mensaje: mensajes) {
				out.listaMensajes.add(new MensajeDTO(mensaje));
			}
			out.code = Code.OK;
		} else {
			out.code = Code.INTERNAL_SERVER_ERROR;
			out.description = "ERROR al obtener mensajes";
		}
		return out;
	}

	public void recordMessage(Mensajes mensaje) {
		// registramos en la DB
		Mensajes messageRecord = mensajesRepository.create(mensaje);
		Chats assoc = chatRepository.findAssoc(mensaje.id_target, mensaje.id_sender);
		if(assoc != null) {
			// update
			assoc.setLast_message_id(messageRecord.id_message);
			chatRepository.update(assoc);
		} else {
			// create
			assoc = new Chats(
					mensaje.id_target,
					mensaje.id_sender,
					messageRecord.id_message
					);
			chatRepository.create(assoc);
		}
	}
	
}
