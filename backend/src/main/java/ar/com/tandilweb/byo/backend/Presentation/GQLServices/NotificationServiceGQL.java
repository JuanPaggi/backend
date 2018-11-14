package ar.com.tandilweb.byo.backend.Presentation.GQLServices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.tandilweb.byo.backend.Filters.JWT.JWTHeader;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.Notification;
import ar.com.tandilweb.byo.backend.Transport.FriendshipsAdapter;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;

@GraphQLApi
@Service
public class NotificationServiceGQL {
	
	@Autowired
	FriendshipsAdapter fsAdapter;

	/**
	 * Servicio destinado al manejo de notificaciones.
	 */
	@GraphQLQuery(name = "NotificationService_getLastNotifications")
	public List<Notification> getUsersClose(@GraphQLEnvironment ResolutionEnvironment environment) {
		// nos traemos el usuario que está llamando a este servicio:
		JWTHeader header = JWTHeader.getHeader(environment);
		Users me = header.getUser();
		// acá vamos a tener que mergear las notificaciones con las de otros tipos
		return fsAdapter.getRequestSendedReceived(me);
	}

}
