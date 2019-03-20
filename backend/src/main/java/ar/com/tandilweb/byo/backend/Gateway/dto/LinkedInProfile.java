package ar.com.tandilweb.byo.backend.Gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ar.com.tandilweb.byo.backend.Gateway.dto.linkedIn.Location;
import ar.com.tandilweb.byo.backend.Gateway.dto.linkedIn.Positions;

// clase creada basada en respuesta de rest.
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkedInProfile {

	private String emailAddress;
	private String firstName;
	private String headline;
	private String id;
	private String industry;
	private String lastName;
	private Location location;
	private String pictureUrl;
	private Positions positions;
	private String publicProfileUrl;
	private String summary;
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public Positions getPositions() {
		return positions;
	}
	public void setPositions(Positions positions) {
		this.positions = positions;
	}
	public String getPublicProfileUrl() {
		return publicProfileUrl;
	}
	public void setPublicProfileUrl(String publicProfileUrl) {
		this.publicProfileUrl = publicProfileUrl;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	
}
