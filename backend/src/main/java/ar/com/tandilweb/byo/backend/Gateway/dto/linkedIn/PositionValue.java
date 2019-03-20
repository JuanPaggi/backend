package ar.com.tandilweb.byo.backend.Gateway.dto.linkedIn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionValue {
	private Company company;
	private long id;
	private boolean isCurrent;
	private LocationCompany location;
	private StartDate startDate;
	private String summary;
	private String title;
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isCurrent() {
		return isCurrent;
	}
	public void setCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}
	public LocationCompany getLocation() {
		return location;
	}
	public void setLocation(LocationCompany location) {
		this.location = location;
	}
	public StartDate getStartDate() {
		return startDate;
	}
	public void setStartDate(StartDate startDate) {
		this.startDate = startDate;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}