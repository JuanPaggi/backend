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
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;
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
	public List<EventDTO> getEvents(
			@GraphQLArgument(name = "latitude") String latitude,
			@GraphQLArgument(name = "longitude") String longitude,
			@GraphQLEnvironment ResolutionEnvironment environment) {
		// nos traemos el usuario que está llamando a este servicio:

		JWTHeader header = JWTHeader.getHeader(environment);
		Users me = header.getUser();

		// casteamos las variables (limitación de graphql)
				double lat = Double.parseDouble(latitude);
				double lon = Double.parseDouble(longitude);
				
		List<EventDTO> out = evAdapter.getEvents(lat,lon, me);

		return out;
	}
	
	@GraphQLQuery(name = "EventService_getStands")
	public List<Stands> getStands(
			@GraphQLArgument(name = "id_event") Long id_event,
			@GraphQLEnvironment ResolutionEnvironment environment){

		
		
		List<Stands> out = null;
		try {
			JWTHeader header = JWTHeader.getHeader(environment);
			if(!header.isTrusted()) throw new Exception("isn't trusteado.");
			out = evAdapter.getStands(id_event);
		} catch (Exception e) {
			log.error("Error obteniendo stands", e);
		}

		return out;
	} 
	
	@GraphQLQuery(name = "EventService_setCheckin")
	public ResponseDTO buscoYOfrezco(
			@GraphQLArgument(name = "id_stand") long id_stand , 
			@GraphQLEnvironment ResolutionEnvironment environment
			) throws Exception {

		JWTHeader header = JWTHeader.getHeader(environment);
		Users usuario = header.getUser();
		ResponseDTO out = new ResponseDTO();

		if(header.isTrusted()) {
			out = evAdapter.setCheckin(id_stand, usuario.getId_user());
		} else {
			out.code = ResponseDTO.Code.INTERNAL_SERVER_ERROR;
			out.description = "Error datos no seteados";
		}

		return out;
	}
	
}
