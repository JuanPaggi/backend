package ar.com.tandilweb.byo.backend.Presentation.GQLServices;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.tandilweb.byo.backend.Filters.JWT.JWTHeader;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ChatsDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ListMessageDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ListaChatsDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;
import ar.com.tandilweb.byo.backend.Transport.ChatAdapter;
import ar.com.tandilweb.byo.backend.Transport.UserAdapter;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;

@GraphQLApi
@Service
public class ChatServiceGQL {
	
	/**
	 * Servicio destinado a la creación de nuevas conversaciones y acciones estáticas del chat.
	 * Recordar que el manejo de presencia/mensajería se debe hacer por un sistema de websockets/STOMP
	 */
	
	private static final Logger log = LoggerFactory.getLogger(ChatServiceGQL.class);

	@Autowired
	private ChatAdapter chatAdapter;
	
	@Autowired
	private UserAdapter userAdapter;
	
	// TODO: para la lista de chats activos hay que reworkear para usar una tabla de conversaciones.
	
	@GraphQLQuery(name = "ChatService_listaInicial")
	public ListMessageDTO listaInicial(
			@GraphQLArgument(name = "id_target") long idtar,
			@GraphQLEnvironment ResolutionEnvironment environment) {
		log.debug("Requesting ChatService_listaInicial");
		ListMessageDTO out = new ListMessageDTO();
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			Users usuario = header.getUser();
			out = chatAdapter.getChatWith(usuario, idtar);
			out.me = userAdapter.getVCardByUser(usuario.getId_user());
			out.target = userAdapter.getVCardByUser(idtar);
		} catch (Exception e) {
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error el servidor a fallado";
		}
		log.debug("Responding ChatService_listaInicial");
		return out;
	}
	
	@GraphQLQuery(name = "ChatService_listaChats")
	public ListaChatsDTO listaChats(
			@GraphQLEnvironment ResolutionEnvironment environment
			) {
		log.debug("Requesting ChatService_listaChats");
		ListaChatsDTO out = new ListaChatsDTO();
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			Users usuario = header.getUser();
			
			List<ChatsDTO> chats = chatAdapter.getConversations(usuario.getId_user());
			
			out.chats = chats;			
			out.code = ResponseDTO.Code.OK;
		} catch (Exception e) {
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error el servidor a fallado";
		}
		log.debug("Responding ChatService_listaChats");
		return out;
	}

}
