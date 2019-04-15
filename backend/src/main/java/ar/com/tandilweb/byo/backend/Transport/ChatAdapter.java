package ar.com.tandilweb.byo.backend.Transport;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;

import ar.com.tandilweb.byo.backend.Model.domain.Chats;
import ar.com.tandilweb.byo.backend.Model.domain.Mensajes;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.ChatRepository;
import ar.com.tandilweb.byo.backend.Model.repository.MensajesRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ChatsDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ListMessageDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.MensajeDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO.Code;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.VCard;
import ar.com.tandilweb.byo.backend.utils.SpCall;
import ar.com.tandilweb.byo.backend.utils.SpCaller;

public class ChatAdapter {
	
	public static Logger logger = LoggerFactory.getLogger(ChatAdapter.class);
	
	@Autowired
	private MensajesRepository mensajesRepository;
	
	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private UserAdapter userAdapter;
	
	@Autowired
	private SpCaller spCaller;
	
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

	public String recordMessage(Mensajes mensaje, boolean is_premium) {
		//CALL `desa_byo`.`addMessage`(1, 8, 'PROBANDO', now(), FALSE, @eid, @ecode, @emsg);
		
		SqlParameter[] paramArray = {
			spCaller.makeInputParameter("p_id_sender", Types.INTEGER),
			spCaller.makeInputParameter("p_id_target", Types.INTEGER),
			spCaller.makeInputParameter("p_message", Types.VARCHAR),
			spCaller.makeInputParameter("p_fecha", Types.DATE),
			spCaller.makeInputParameter("p_is_premium", Types.BOOLEAN),
			spCaller.makeOutputParameter("out_message_id", Types.INTEGER),
			spCaller.makeOutputParameter("out_ecode", Types.TINYINT),
			spCaller.makeOutputParameter("out_emsg", Types.VARCHAR),
		};
		SpCall call = spCaller.callSp("addMessage", paramArray);
		Map<String, Object> result = call.execute(
			mensaje.id_sender, 
			mensaje.id_target, 
			mensaje.message,
			new Date(),
			is_premium
		);
		
		if(result.get("out_ecode") == null) {
			long id_message = Long.valueOf(result.get("out_message_id").toString());
			return "OK";
		} else {
			if("1".equals(result.get("out_ecode").toString())) {
				// LIMITE DE MENSAJES DIARIOS
			}
			return result.get("out_emsg").toString();
		}
		//logger.debug("DEBUG RESPONSE SPCALLER " + result.get("out_emsg"));
		//
		
		// registramos en la DB
//		Mensajes messageRecord = mensajesRepository.create(mensaje);
//		Chats assoc = chatRepository.findAssoc(mensaje.id_target, mensaje.id_sender);
//		if(assoc != null) {
//			// update
//			assoc.setLast_message_id(messageRecord.id_message);
//			chatRepository.update(assoc);
//		} else {
//			// create
//			assoc = new Chats(
//					mensaje.id_target,
//					mensaje.id_sender,
//					messageRecord.id_message
//					);
//			chatRepository.create(assoc);
//		}
	}
	
	public List<ChatsDTO> getConversations(long me) {
		List<Chats> chats = chatRepository.findLimitedById(me, 25);
		List<ChatsDTO> chatsOut = new ArrayList<ChatsDTO>();
		for(Chats chat: chats) {
			ChatsDTO dto = new ChatsDTO();
			long otherID = chat.getId_user_requester() != me ? chat.getId_user_requester() : chat.getId_user_sender();
			VCard user = userAdapter.getVCardByUser(otherID);
			dto.id_usuario = otherID;
			dto.vcard = user;
			Mensajes mensaje = mensajesRepository.findById(chat.getLast_message_id());
			dto.fecha = mensaje.fechaStr;
			dto.mensaje = mensaje.message;
			chatsOut.add(dto);
		}
		return chatsOut;
	}
	
}
