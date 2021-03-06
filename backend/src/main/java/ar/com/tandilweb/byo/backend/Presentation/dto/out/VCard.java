package ar.com.tandilweb.byo.backend.Presentation.dto.out;

import io.leangen.graphql.annotations.GraphQLQuery;

public class VCard {
	
	@GraphQLQuery(name = "id_usuario")
	public long id_usuario;
	@GraphQLQuery(name = "nombre")
	public String nombre;
	@GraphQLQuery(name = "titulo")
	public String titulo; // arquitecto, desarrollador, etc.
	@GraphQLQuery(name = "busco")
	public String busco;
	@GraphQLQuery(name = "ofrezco")
	public String ofrezco;
	@GraphQLQuery(name = "picture")
	public String picture;
	@GraphQLQuery(name = "pais")
	public String pais;
	@GraphQLQuery(name = "sector")
	public String sector;
	@GraphQLQuery(name = "puesto_actual")
	public String puesto_actual;
	@GraphQLQuery(name = "empresa")
	public String empresa;
	@GraphQLQuery(name = "sinopsis")
	public String sinopsis; // sumary en linkedin
	@GraphQLQuery(name = "linkedin_link")
	public String linkedin_link;
	// hay que ver que tiene headline
	public String getNombre() {
		return this.nombre;
	}

}
