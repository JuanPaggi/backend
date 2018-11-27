package ar.com.tandilweb.byo.backend.Transport;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.tandilweb.byo.backend.Model.domain.Profile;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.GpsDataRepository;
import ar.com.tandilweb.byo.backend.Model.repository.ProfileRepository;
import ar.com.tandilweb.byo.backend.Model.repository.UserRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.VCard;

public class GpsAdapter {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
	private UserAdapter userAdapter;
	
	@Autowired
	private GpsDataRepository gpsRepository;
	
	public List<VCard> getUsersClose(double lat, double lon, Users userRequester) {
		// hay que verificar antes que nada que los parametros estén ok
		// en este caso lat y lon pueden ser valores negativos, cero o positivos asique no hace falta revisarlo
		// userRequester puede ser null si no queremos filtrar al usuario que consulta (al reutilizar la funcion en otro lugar):
		// objeto de salida:
		List<VCard> vcards = new ArrayList<VCard>();
		// hacer busqueda por lat long, de momento traemos todo para facilitar las pruebas:
		
		//List<Users> users = userRepository.getAllNotContacts(10, userRequester.getId_user());
		List<Users> users = gpsRepository.getUsersClose(lat, lon, 70,userRequester.getId_user());
		
		// chequeamos que tengamos resultados:
		if(users != null) {
			// creamos y empaquetamos las vcard
			for(Users user: users) {
				// si no hay filtro de usuario o no somos nosotros mismos:
				if(userRequester == null || userRequester.getId_user() != user.getId_user()) {
					VCard vcard = new VCard();
					// nos traemos el perfil por los datos que faltan:
					/**
					 * !!!! TENER EN CUENTA !!!!
					 * esto está hecho así para poder probar las solicitudes de amistad
					 * en realidad esta función debería traer los usuarios por cercanía
					 * cuando se cree esa función de busqueda por cercanía ya nos traemos el perfil
					 * para no hacer multiples consultas separadas, directamente le metemos un join.
					 */
					vcards.add(userAdapter.getVCardByUser(user));
				}
			}
		}
		/* devolvemos la lista de vcards, no un ResponseDTO porque es 
		 * posible que queramos reutilizar en otro punto del sistema la consulta y 
		 * no vamos a crear otra para que devuelva otra cosa, hay que hacerlo genérico
		 */
		return vcards;
	}
	
}
