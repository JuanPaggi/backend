package ar.com.tandilweb.byo.backend.Presentation.GQLServices;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.tandilweb.byo.backend.Filters.JWT.JWTHeader;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.VCard;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.VCardList;
import ar.com.tandilweb.byo.backend.Transport.GpsAdapter;
import ar.com.tandilweb.byo.backend.Transport.UserAdapter;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;

@GraphQLApi
@Service
public class GeoposysServiceGQL {
	
	private static final Logger log = LoggerFactory.getLogger(GeoposysServiceGQL.class);
	
	@Autowired
	private GpsAdapter gpsAdapter;
	
	@Autowired
	private UserAdapter userAdapter;
	
	/**
	 * Servicio destinado al manejo de datos de GPS, alta de datos, busquedas por datos de gps, etc.
	 */
	
	// función para obtener los usuarios cercanos.
	@GraphQLQuery(name = "GeoposysService_getUsersClose")
	public VCardList getUsersClose(
			@GraphQLArgument(name = "latitude") String latitude,
			@GraphQLArgument(name = "longitude") String longitude,
			@GraphQLArgument(name = "distanciaDeBusqueda") String distanciaDeBusqueda,
			@GraphQLEnvironment ResolutionEnvironment environment
			) {
		log.debug("Requesting GeoposysService_getUsersClose");
		VCardList out;
		// nos traemos el usuario que está llamando a este servicio:
		JWTHeader header = JWTHeader.getHeader(environment);
		Users me = header.getUser();

		// casteamos las variables (limitación de graphql)
		double lat = Double.parseDouble(latitude);
		double lon = Double.parseDouble(longitude);
		int dist = Integer.parseInt(distanciaDeBusqueda);
		if (dist > 100) {
			dist = 100;
		}
		// bloque de contención de excepciones:
		try {
			// seteamos la ubicación para este usuario
			userAdapter.setGeoLocation(lat, lon, me);
			// nos traemos los usuarios cercanos a esta latitud y longitud
			List<VCard> users = gpsAdapter.getUsersClose(lat, lon, me, dist);
			// devolvemos la lista de vcards en un dto de respuesta con codigo si es que hay 
			if(users != null) {
				out = new VCardList(ResponseDTO.Code.OK, "OK", users);
			} else {
				out = new VCardList(ResponseDTO.Code.NOT_FOUND, "No hay usuarios cerca.");
			}
		} catch (Exception e) {
			// si alguna función arroja un error:
			log.error("Error al obtener usuarios cercanos", e);
			out = new VCardList(ResponseDTO.Code.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		log.debug("Responding GeoposysService_getUsersClose");
		return out;
	}
	
}
