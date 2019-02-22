package ar.com.tandilweb.byo.backend.Presentation.GQLServices;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.tandilweb.byo.backend.Filters.JWT.JWTHeader;
import ar.com.tandilweb.byo.backend.Model.domain.Stands;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.EventDTO;
import ar.com.tandilweb.byo.backend.Transport.EventsAdapter;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;

@GraphQLApi
@Service
public class EventServiceGQL {
	@Autowired
	EventsAdapter evAdapter;
	/**
	 * Servicio destinado al manejo de datos de la pantallas relacionadas con eventos.
	 */
	
	private static final Logger log = LoggerFactory.getLogger(EventServiceGQL.class);
	
	@GraphQLQuery(name = "EventService_getEvents")
	public List<EventDTO> getEvents(@GraphQLEnvironment ResolutionEnvironment environment) {
		// nos traemos el usuario que está llamando a este servicio:
		JWTHeader header = JWTHeader.getHeader(environment);
		Users me = header.getUser();
		
		return evAdapter.getEvents();
	}
	
	@GraphQLQuery(name = "EventService_getStands")
	public List<Stands> getStands(
			@GraphQLArgument(name = "id_event") Long id_event,
			@GraphQLEnvironment ResolutionEnvironment environment){
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			if(!header.isTrusted()) throw new Exception("isn't trusteado.");
			return evAdapter.getStands(id_event);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	} 
	
	@GraphQLQuery(name = "EventService_getCheckin")
	public Boolean getCheckin(
			@GraphQLArgument(name = "stand") Integer stand,
			@GraphQLEnvironment ResolutionEnvironment environment){
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			if(!header.isTrusted()) throw new Exception("isn't trusteado.");
			return evAdapter.getCheckin(stand,header.getUser().getId_user());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	} 
}
