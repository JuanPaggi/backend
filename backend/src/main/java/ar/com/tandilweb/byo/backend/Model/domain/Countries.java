package ar.com.tandilweb.byo.backend.Model.domain;

public class Countries {
	
	private long id_country;
	private String name;
	private String code;
		
	public Countries() {}
	
	public Countries(long id, String name, String code) {
		this.id_country = id;
		this.name = name;
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	public long getId_country() {
		return id_country;
	}

	public String getName() {
		return name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setId_country(long id_country) {
		this.id_country = id_country;
	}

	public void setName(String name) {
		this.name = name;
	}
		
}
