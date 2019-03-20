package ar.com.tandilweb.byo.backend.Presentation.WSockets;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import ar.com.tandilweb.byo.backend.Presentation.WSockets.DTO.ClientData;

public class UserService {

private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	protected Map<String, ClientData> clients = new HashMap<String, ClientData>();
	
	@Autowired
	private SimpMessagingTemplate msgTmp;
	
	public void addClient(ClientData data) {
		clients.put(data.userID, data);
	}
	
	public ClientData getClient(String userID) {
		return this.clients.get(userID);
	}
	
	private MessageHeaders createHeaders(String sessionId) {
	    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
	    headerAccessor.setSessionId(sessionId);
	    headerAccessor.setLeaveMutable(true);
	    return headerAccessor.getMessageHeaders();
	}
	
	public void sendToSessID(String direction, String sessionID, Object payload) {
		log.debug("Sending message to a sessionID");
		msgTmp.convertAndSendToUser(sessionID, direction, payload, createHeaders(sessionID));
	}
	
}
