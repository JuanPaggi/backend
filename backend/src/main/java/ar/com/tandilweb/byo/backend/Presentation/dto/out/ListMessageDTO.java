package ar.com.tandilweb.byo.backend.Presentation.dto.out;

import java.util.List;

import io.leangen.graphql.annotations.GraphQLQuery;

public class ListMessageDTO extends ResponseDTO{
	@GraphQLQuery(name = "listaMensajes")
	public List<MensajeDTO> listaMensajes;
}
