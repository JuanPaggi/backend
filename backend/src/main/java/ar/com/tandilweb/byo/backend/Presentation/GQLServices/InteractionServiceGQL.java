package ar.com.tandilweb.byo.backend.Presentation.GQLServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.tandilweb.byo.backend.Filters.JWT.JWTHeader;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.LoginOut;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.VCardList;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO.Code;
import ar.com.tandilweb.byo.backend.Transport.FriendshipsAdapter;
import ar.com.tandilweb.byo.backend.Transport.LinkedInAdapter;
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
	
	private static final Logger log = LoggerFactory.getLogger(AccountServiceGQL.class);

	@Autowired
	private FriendshipsAdapter friendshipAdapter;
	
	@GraphQLQuery(name = "InteractionService_friendshipRequest")
	public ResponseDTO friendshipRequest(
			@GraphQLArgument(name = "id_user_target") long idtar,
			@GraphQLEnvironment ResolutionEnvironment environment){
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			Users usuario = header.getUser();
			return friendshipAdapter.validateFriendshipRequest(usuario.getId_user(),idtar);
		} catch (Exception e) {
			e.printStackTrace();
			ResponseDTO out = new LoginOut();
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error el servidor a fallado";
			return out;
		}
	}

	@GraphQLQuery(name = "InteractionService_acceptFriend")
	public ResponseDTO acceptFriend(
			@GraphQLArgument(name = "id_user_requester") long idreq,
			@GraphQLEnvironment ResolutionEnvironment environment){
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			Users usuario = header.getUser();
			return friendshipAdapter.validateFriendAcceptance(idreq, usuario.getId_user());
		} catch (Exception e) {
			e.printStackTrace();
			ResponseDTO out = new LoginOut();
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error el servidor a fallado";
			return out;
		}
	}
	
	@GraphQLQuery(name = "InteractionService_cancelFriendRequest")
	public ResponseDTO cancelFriendRequest(
			@GraphQLArgument(name = "id_user_target") long idtar,
			@GraphQLEnvironment ResolutionEnvironment environment){
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			Users usuario = header.getUser();
			return friendshipAdapter.cancelFriendRequest(usuario.getId_user(), idtar);
		} catch (Exception e) {
			e.printStackTrace();
			ResponseDTO out = new LoginOut();
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error el servidor a fallado";
			return out;
		}
	}
	
	
	@GraphQLQuery(name = "InteractionService_getContacts")
	public VCardList getContacts(
			@GraphQLEnvironment ResolutionEnvironment environment){
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			if(!header.isTrusted()) throw new Exception("isn't trusteado.");
			Users usuario = header.getUser();
			return new VCardList(Code.ACCEPTED, "ok.", friendshipAdapter.getFriends(usuario));
		} catch (Exception e) {
			return new VCardList(ResponseDTO.Code.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GraphQLQuery(name = "InteractionService_findContact")
	public VCardList findContact(
			@GraphQLEnvironment ResolutionEnvironment environment){
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			if(!header.isTrusted()) throw new Exception("isn't trusteado.");
			Users usuario = header.getUser();
			String busqueda = "eze";
			return new VCardList(Code.ACCEPTED, "ok.", friendshipAdapter.getFriendsFiltered(usuario, busqueda));
		} catch (Exception e) {
			return new VCardList(ResponseDTO.Code.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
