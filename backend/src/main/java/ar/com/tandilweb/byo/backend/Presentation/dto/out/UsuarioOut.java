package ar.com.tandilweb.byo.backend.Presentation.dto.out;

import io.leangen.graphql.annotations.GraphQLQuery;

public class UsuarioOut {
	
	private Integer identificador;
	private String nick;
	
	@GraphQLQuery(name = "nick", description = "nick del usuario")
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	@GraphQLQuery(name = "idUsuario", description = "identificador del usuario")
	public Integer getIdentificador() {
		return identificador;
	}
	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

}
