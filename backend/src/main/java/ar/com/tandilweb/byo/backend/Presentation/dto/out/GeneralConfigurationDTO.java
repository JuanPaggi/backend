package ar.com.tandilweb.byo.backend.Presentation.dto.out;



import io.leangen.graphql.annotations.GraphQLQuery;

public class GeneralConfigurationDTO {
	public GeneralConfigurationDTO() {};
	
	public GeneralConfigurationDTO(long id, String valor) {
		this.id = id;
		this.valor = valor;
	}

	@GraphQLQuery(name = "id") 
	private long id;
	
	@GraphQLQuery(name = "clave") 
	public String clave;

	@GraphQLQuery(name = "valor") 
	public String valor;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	




	

}
