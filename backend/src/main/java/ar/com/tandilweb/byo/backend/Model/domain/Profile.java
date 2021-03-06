package ar.com.tandilweb.byo.backend.Model.domain;

public class Profile {

	private long id_user;
	private String headline;
	private String industry;
	private Countries country;
	private String location;
	private String linkedin_url;
	private String summary;
	private String current_position;
	private String company_name;
		
	public Profile(){}
	
	public Profile(long id_user, String headline, String industry, String location, String linkedin_url, String summary, String current_position, String company_name) {
		this.id_user = id_user;
		this.headline = headline;
		this.industry = industry;
		//this.country = country;
		this.location = location;
		this.linkedin_url = linkedin_url;
		this.summary = summary;
		this.current_position = current_position;
		this.company_name = company_name;
	}

	public long getId_user() {
		return id_user;
	}

	public void setId_user(long id_user) {
		this.id_user = id_user;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public Countries getCountry() {
		return country;
	}

	public void setCountry(Countries country) {
		this.country = country;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLinkedin_url() {
		return linkedin_url;
	}

	public void setLinkedin_url(String linkedin_url) {
		this.linkedin_url = linkedin_url;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCurrent_position() {
		return current_position;
	}

	public void setCurrent_position(String current_position) {
		this.current_position = current_position;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	
}
