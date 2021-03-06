package ar.com.tandilweb.byo.backend.Presentation.GQLServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.tandilweb.byo.backend.Filters.JWT.JWTHeader;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.LoginOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO.Code;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.VCardList;
import ar.com.tandilweb.byo.backend.Transport.FriendshipsAdapter;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;

@GraphQLApi
@Service
public class InteractionServiceGQL {
	
	/**
	 * Servicio para administrar interacciones entre usuarios, solicitudes de amistad, bloqueos, etc.
	 * Abarca: 
	 * solicitudes de amistad de cualquier pantalla, bloqueo de usuarios de cualquier pantalla,
	 * Lista de contactos.
	 */
	
	private static final Logger log = LoggerFactory.getLogger(InteractionServiceGQL.class);

	@Autowired
	private FriendshipsAdapter friendshipAdapter;
	
	@GraphQLQuery(name = "InteractionService_friendshipRequest")
	public ResponseDTO friendshipRequest(
			@GraphQLArgument(name = "id_user_target") long idtar,
			@GraphQLEnvironment ResolutionEnvironment environment){
		log.debug("Requesting InteractionService_friendshipRequest");
		ResponseDTO out;
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			Users usuario = header.getUser();
			out = friendshipAdapter.validateFriendshipRequest(usuario.getId_user(),idtar);
		} catch (Exception e) {
			e.printStackTrace();
			out = new LoginOut();
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error el servidor ha fallado";
		}
		log.debug("Responding InteractionService_friendshipRequest");
		return out;
	}

	@GraphQLQuery(name = "InteractionService_acceptFriend")
	public ResponseDTO acceptFriend(
			@GraphQLArgument(name = "id_user_requester") long idreq,
			@GraphQLEnvironment ResolutionEnvironment environment){
		log.debug("Requesting InteractionService_acceptFriend");
		ResponseDTO out;
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			Users usuario = header.getUser();
			out = friendshipAdapter.validateFriendAcceptance(idreq, usuario.getId_user());
		} catch (Exception e) {
			e.printStackTrace();
			out = new LoginOut();
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error el servidor ha fallado";
		}
		log.debug("Responding InteractionService_acceptFriend");
		return out;
	}
	
	@GraphQLQuery(name = "InteractionService_cancelFriendRequest")
	public ResponseDTO cancelFriendRequest(
			@GraphQLArgument(name = "id_user_target") long idtar,
			@GraphQLEnvironment ResolutionEnvironment environment){
		log.debug("Requesting InteractionService_cancelFriendRequest");
		ResponseDTO out = new LoginOut();
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			Users usuario = header.getUser();
			out = friendshipAdapter.cancelFriendRequest(usuario.getId_user(), idtar);;
		} catch (Exception e) {
			e.printStackTrace();
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error el servidor ha fallado";
		}
		log.debug("Responding InteractionService_cancelFriendRequest");
		return out;
	}
	@GraphQLQuery(name = "InteractionService_rejectFriendRequest")
	public ResponseDTO rejectFriendRequest(
			@GraphQLArgument(name = "id_user_target") long idtar,
			@GraphQLEnvironment ResolutionEnvironment environment){
		log.debug("Requesting InteractionService_rejectFriendRequest");
		ResponseDTO out = new LoginOut();
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			Users usuario = header.getUser();
			out = friendshipAdapter.rejectFriendRequest(usuario.getId_user(), idtar);
		} catch (Exception e) {
			log.error("Error rejectFriendRequest", e);
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error el servidor ha fallado";
		}
		log.debug("Responding InteractionService_rejectFriendRequest");
		return out;
	}
	
	
	@GraphQLQuery(name = "InteractionService_getContacts")
	public VCardList getContacts(
			@GraphQLEnvironment ResolutionEnvironment environment){
		log.debug("Requesting InteractionService_getContacts");
		VCardList out;
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			if(!header.isTrusted()) throw new Exception("isn't trusteado.");
			Users usuario = header.getUser();
			out = new VCardList(Code.ACCEPTED, "ok.", friendshipAdapter.getFriends(usuario));
		} catch (Exception e) {
			out = new VCardList(ResponseDTO.Code.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		log.debug("Responding InteractionService_getContacts");
		return out;
	}
	
	@GraphQLQuery(name = "InteractionService_findContact")
	public VCardList findContact(
			@GraphQLEnvironment ResolutionEnvironment environment){
		VCardList out;
		log.debug("Requesting InteractionService_findContact");
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			if(!header.isTrusted()) throw new Exception("isn't trusteado.");
			Users usuario = header.getUser();
			String busqueda = "eze";
			out = new VCardList(Code.ACCEPTED, "ok.", friendshipAdapter.getFriendsFiltered(usuario, busqueda));
		} catch (Exception e) {
			out = new VCardList(ResponseDTO.Code.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		log.debug("Responding InteractionService_findContact");
		return out;
	}
}
