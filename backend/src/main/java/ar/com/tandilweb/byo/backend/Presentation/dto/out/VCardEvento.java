package ar.com.tandilweb.byo.backend.Presentation.dto.out;

import java.util.Calendar;

import io.leangen.graphql.annotations.GraphQLQuery;

public class VCardEvento {

	@GraphQLQuery(name = "id_evento")
	public long id_evento;
	@GraphQLQuery(name = "nombre")
	public String nombre;
	@GraphQLQuery(name = "picture")
	public String picture;
	@GraphQLQuery(name = "pais")
	public String pais;
	@GraphQLQuery(name = "sector")
	public String sector;
	@GraphQLQuery(name = "empresa")
	public String empresa;
	@GraphQLQuery(name = "sinopsis")
	public String sinopsis; // sumary en linkedin
	@GraphQLQuery(name = "lugar")
	public String lugar;
	@GraphQLQuery(name = "direccion")
	public String direccion;
	@GraphQLQuery(name = "fecha")
	public long fecha;
	public VCardEvento(long id_event, String name, String picture, String pais, String sector, String empresa,
			String sinopsis, String lugar, String direccion, Calendar cal) {
		
		this.id_evento = id_event;
		this.picture = picture;
		this.nombre = name;
		this.pais = pais;
		this.sector = sector;
		this.empresa = empresa;
		this.sinopsis = sinopsis;
		this.lugar = lugar;
		this.direccion = direccion;
		this.fecha = cal.getTimeInMillis();

	}
	// hay que ver que tiene headline
	public String getNombre() {
		return this.nombre;
	}

}
