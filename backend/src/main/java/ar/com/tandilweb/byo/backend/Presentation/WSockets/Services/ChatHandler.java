package ar.com.tandilweb.byo.backend.Presentation.WSockets.Services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import ar.com.tandilweb.byo.backend.Gateway.fcm.FirebaseCloudMessaging;
import ar.com.tandilweb.byo.backend.Gateway.fcm.HttpFCMPayload;
import ar.com.tandilweb.byo.backend.Model.domain.Mensajes;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.UserRepository;
import ar.com.tandilweb.byo.backend.Presentation.WSockets.UserService;
import ar.com.tandilweb.byo.backend.Presentation.WSockets.DTO.ClientData;
import ar.com.tandilweb.byo.backend.Presentation.WSockets.DTO.MessageDataIn;
import ar.com.tandilweb.byo.backend.Presentation.WSockets.DTO.Response;
import ar.com.tandilweb.byo.backend.Transport.ChatAdapter;

@Controller
@MessageMapping("/chatHandler")
public class ChatHandler {

	private static final Logger log = LoggerFactory.getLogger(ChatHandler.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private ChatAdapter chatAdapter;
	
	@Autowired
	private FirebaseCloudMessaging firebaseCloudMessaging;
	
	@Value("${fcm.serverkey}")
	private String serverKey;
	
	@Autowired
	private UserRepository userRepository;

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
	@SendToUser("/chatService/data")
	public Mensajes send(MessageDataIn msg, SimpMessageHeaderAccessor headerAccessor) {
		
		ClientData cd = userService.getClient(msg.userID);
		if (cd != null && cd.socketID.equals(headerAccessor.getSessionId()) && msg.targetID != null) {
			Mensajes mensaje = new Mensajes();
			mensaje.fecha = new Date();
			SimpleDateFormat dt = new SimpleDateFormat("HH:mm"); 
			mensaje.fechaStr = dt.format(mensaje.fecha);
			mensaje.id_sender = Long.parseLong(msg.userID);
			mensaje.id_target = Long.parseLong(msg.targetID);
			mensaje.message = msg.message;
			mensaje.is_viewed = false;
			
			// chequeamos si ya hay alguien en el sistema de chat con este targetID
			ClientData cdTarget = userService.getClient(msg.targetID);
			Users sender = userRepository.findById(mensaje.id_sender);
			String result = chatAdapter.recordMessage(mensaje, sender.isPremium());

			if(cdTarget != null && "OK".equals(result)) {
				// enviamos el mensaje por el sistema de chat
				userService.sendToSessID("/chatService/data", cdTarget.socketID, mensaje);
				Users target = userRepository.findById(mensaje.id_target);
				if(target.getReceiveNotifications()) {
					// enviamos notificacion
					List<String> rids = new ArrayList<String>();
					HttpFCMPayload payload = new HttpFCMPayload("Mensaje recibido", "Recibiste un mensaje de " + sender.getFirst_name() + " " + sender.getLast_name());
					firebaseCloudMessaging.setServerKey(serverKey);
					rids.add(target.getFcmToken());
					payload.setTarget(target.getFcmToken());
					firebaseCloudMessaging.send(payload);
				}
			}
			
			if(!"OK".equals(result)) {
				mensaje.error = true;
				mensaje.errorMsg = result;
			}
			return mensaje;
			
		}
		return null;
		
	}
	
	

}
