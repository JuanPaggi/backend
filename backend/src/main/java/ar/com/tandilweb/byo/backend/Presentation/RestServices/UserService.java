package ar.com.tandilweb.byo.backend.Presentation.RestServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.tandilweb.byo.backend.Transport.UsuariosAdapter;

@RestController
@RequestMapping("/userService")
public class UserService {
	
	@Autowired
	private UsuariosAdapter usuarioAdapter;
	
	@RequestMapping(path="/nuevoUsuario", method = RequestMethod.GET)
	public boolean nuevoRegistro(@RequestParam(value="nick") String nick, @RequestParam(value="password") String pass) {
		//return tablaTestAdapter.nuevoRegistro();
		try {
			// probando
			if(nick == null || nick.trim().length() < 3) throw new Exception("El nick no debe estar vacío");
			if(pass == null || pass.trim().length() < 8) throw new Exception("La password es invalida");
			return usuarioAdapter.crearUsuario(nick.trim(), pass.trim());
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
	}

}
