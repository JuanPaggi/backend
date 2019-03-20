package ar.com.tandilweb.byo.backend.Presentation.dto.out;

import java.util.List;

import io.leangen.graphql.annotations.GraphQLQuery;

public class ListaChatsDTO extends ResponseDTO {
	
	@GraphQLQuery(name = "chats")
	public List<ChatsDTO> chats;
}
