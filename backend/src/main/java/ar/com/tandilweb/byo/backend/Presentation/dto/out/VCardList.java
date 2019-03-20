package ar.com.tandilweb.byo.backend.Presentation.dto.out;

import java.util.List;

import io.leangen.graphql.annotations.GraphQLQuery;

public class VCardList extends ResponseDTO {
	
	public VCardList(Code code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public VCardList(Code code, String description, List<VCard> vcards) {
		this.code = code;
		this.description = description;
		this.vcards = vcards;
	}

	@GraphQLQuery(name = "vcards", description = "Lista de vcards de usuarios encontrados")
	public List<VCard> vcards;
	
}
