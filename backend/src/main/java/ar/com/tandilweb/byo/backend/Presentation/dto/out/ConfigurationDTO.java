package ar.com.tandilweb.byo.backend.Presentation.dto.out;





import java.util.ArrayList;
import java.util.List;


import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO.Code;
import io.leangen.graphql.annotations.GraphQLQuery;

public class ConfigurationDTO {
	public ConfigurationDTO() {};
	
	@GraphQLQuery(name = "configurations") 
	private List<GeneralConfigurationDTO> configurations = new ArrayList<GeneralConfigurationDTO>();
	
	@GraphQLQuery(name = "description") 
	public String description;

	@GraphQLQuery(name = "code") 
	public Code code;
	

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<GeneralConfigurationDTO> getConfigurations() {
		return configurations;
	}

	public void setConfigurations(List<GeneralConfigurationDTO> configurations) {
		this.configurations = configurations;
	}

	public void add(GeneralConfigurationDTO gc) {
		this.configurations.add(gc);
	}


	

}
