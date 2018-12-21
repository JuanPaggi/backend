package ar.com.tandilweb.byo.backend.Presentation.WSockets.Services;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import ar.com.tandilweb.byo.backend.Model.domain.Mensajes;
import ar.com.tandilweb.byo.backend.Presentation.WSockets.UserService;
import ar.com.tandilweb.byo.backend.Presentation.WSockets.DTO.ClientData;
import ar.com.tandilweb.byo.backend.Presentation.WSockets.DTO.MessageDataIn;
import ar.com.tandilweb.byo.backend.Presentation.WSockets.DTO.Response;

@Controller
@MessageMapping("/chatHandler")
public class ChatHandler {

	private static final Logger log = LoggerFactory.getLogger(ChatHandler.class);

	@Autowired
	private UserService userService;

	@MessageMapping("/start")
	// @SendTo("/userService/popup")
	// @SendToUser("/userService/start")
	@SendToUser("/clientInterceptor/data")
	public Response start(ClientData data, SimpMessageHeaderAccessor headerAccessor) {
		Response popup = new Response();
		log.debug("Nuevo Usuario: " + headerAccessor.getSessionId());
		data.socketID = headerAccessor.getSessionId();
		if("22365".equals(data.authBearer)) { // TODO: mejorar este if por un rest que autentifique
			this.userService.addClient(data);
			popup.message = "Buenaaa";
			//userService.sendToSessID("/clientInterceptor/data", headerAccessor.getSessionId(), popup);
		}
		return popup;
	}
	
	@MessageMapping("/send")
	public void send(MessageDataIn msg, SimpMessageHeaderAccessor headerAccessor) {
		
		ClientData cd = userService.getClient(msg.userID);
		if (cd != null && cd.socketID.equals(headerAccessor.getSessionId()) && msg.targetID != null) {
			
			Mensajes mensaje = new Mensajes();
			mensaje.fecha = new Date();
			mensaje.id_sender = Long.parseLong(msg.userID);
			mensaje.id_target = Long.parseLong(msg.targetID);
			mensaje.message = msg.message;
			
			// chequeamos si ya hay alguien en el sistema de chat con este targetID
			ClientData cdTarget = userService.getClient(msg.targetID);
			if(cdTarget != null) {
				// enviamos el mensaje por el sistema de chat
				userService.sendToSessID("/clientInterceptor/data", cdTarget.socketID, mensaje);
			} else {
				// enviamos notificacion
				
			}
			// registramos en la DB
			
			
		}
		
	}
	
	

}
