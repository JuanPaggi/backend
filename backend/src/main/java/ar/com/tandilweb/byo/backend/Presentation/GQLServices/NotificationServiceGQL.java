package ar.com.tandilweb.byo.backend.Presentation.GQLServices;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.tandilweb.byo.backend.Filters.JWT.JWTHeader;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.Notification;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;
import ar.com.tandilweb.byo.backend.Transport.FriendshipsAdapter;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;

@GraphQLApi
@Service
public class NotificationServiceGQL {
	
	private static final Logger log = LoggerFactory.getLogger(NotificationServiceGQL.class);
	
	@Autowired
	FriendshipsAdapter fsAdapter;

	/**
	 * Servicio destinado al manejo de notificaciones.
	 */
	@GraphQLQuery(name = "NotificationService_getLastNotifications")
	public List<Notification> getUsersClose(@GraphQLEnvironment ResolutionEnvironment environment) {
		log.debug("Requesting NotificationService_getLastNotifications");
		// nos traemos el usuario que está llamando a este servicio:
		JWTHeader header = JWTHeader.getHeader(environment);
		Users me = header.getUser();
		List<Notification> out = fsAdapter.getRequestSendedReceived(me);
		log.debug("Responding NotificationService_markRequestNotificationsAsViewed");
		// acá vamos a tener que mergear las notificaciones con las de otros tipos
		return out;
	}
	
	@GraphQLQuery(name = "NotificationService_getLastNotificationsNumber")
	public ResponseDTO getLastNotificationsNumber(@GraphQLEnvironment ResolutionEnvironment environment) {
		log.debug("Requesting NotificationService_getLastNotificationsNumber");
		JWTHeader header = JWTHeader.getHeader(environment);
		Users me = header.getUser();
		ResponseDTO out = fsAdapter.getLastNotificationsNumber(me.getId_user());
		log.debug("Responding NotificationService_markRequestNotificationsAsViewed");
		return out;
	}
	
	@GraphQLQuery(name = "NotificationService_markRequestNotificationsAsViewed")
	public ResponseDTO markRequestedNotificationsAsViewed(@GraphQLEnvironment ResolutionEnvironment environment) {
		log.debug("Requesting NotificationService_markRequestNotificationsAsViewed");
		JWTHeader header = JWTHeader.getHeader(environment);
		Users me = header.getUser();
		ResponseDTO out = fsAdapter.markRequestNotificationsAsViewed(me.getId_user());
		log.debug("Responding NotificationService_markRequestNotificationsAsViewed");
		return out;
	}


}
