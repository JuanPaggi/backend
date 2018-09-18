package ar.com.tandilweb.byo.backend.Model.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Countries {
	
	@Id
	private long id_country;
	
	@NotNull
	private String name;

	public long getId_country() {
		return id_country;
	}

	public void setId_country(long id_country) {
		this.id_country = id_country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		
}
