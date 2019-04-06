package ar.com.tandilweb.byo.backend.Presentation.dto.out;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ar.com.tandilweb.byo.backend.Model.domain.GpsData;
import ar.com.tandilweb.byo.backend.Model.domain.Stands;
import ar.com.tandilweb.byo.backend.Model.domain.StandsCheckin;
import io.leangen.graphql.annotations.GraphQLQuery;

public class ConfigurationDTO {
	
	@GraphQLQuery(name = "configurations") 
	private HashMap<Long,String> configurations;

	public HashMap<Long, String> getConfigurations() {
		return configurations;
	}

	public void setConfigurations(HashMap<Long, String> configurations) {
		this.configurations = configurations;
	}



	

}
