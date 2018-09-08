package ar.com.tandilweb.byo.backend.Transport;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.tandilweb.byo.backend.Model.domain.Usuarios;
import ar.com.tandilweb.byo.backend.Model.repository.UsuariosRepository;

public class UsuariosAdapter {
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	
	public boolean crearUsuario(String usuario, String password) {
		Usuarios usuarioObj = new Usuarios();
		usuarioObj.setFecha_registro(new Date());
		usuarioObj.setNick(usuario);
		usuarioObj.setPassword(password);
		usuariosRepository.save(usuarioObj);
		return true;
	}
	
}
