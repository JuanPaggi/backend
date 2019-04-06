package ar.com.tandilweb.byo.backend.Presentation.dto.out;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ar.com.tandilweb.byo.backend.Model.domain.GpsData;
import ar.com.tandilweb.byo.backend.Model.domain.Stands;
import ar.com.tandilweb.byo.backend.Model.domain.StandsCheckin;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO.Code;
import io.leangen.graphql.annotations.GraphQLQuery;

public class ConfigurationDTO {
	
	@GraphQLQuery(name = "configurations") 
	private HashMap<Long,String> configurations;
	
	@GraphQLQuery(name = "description") 
	public String description;

	@GraphQLQuery(name = "description") 
	public Code code;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public HashMap<Long, String> getConfigurations() {
		return configurations;
	}

	public void setConfigurations(HashMap<Long, String> configurations) {
		this.configurations = configurations;
	}

	public void add(Long id, String valor) {
		this.configurations.put(id, valor);
	}


	

}
