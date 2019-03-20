package ar.com.tandilweb.byo.backend.Gateway.dto.linkedIn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location{
	private LocationStructure country;
	private String name;
	public LocationStructure getCountry() {
		return country;
	}
	public void setCountry(LocationStructure country) {
		this.country = country;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}