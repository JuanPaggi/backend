package ar.com.tandilweb.byo.backend.Model.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class RememberTokens {
	
	@OneToOne(cascade=CascadeType.ALL)  
	@JoinColumn(name = "id_user")
	@MapsId
	private Users user;
	
	@Id
	private long id_user;
	
	@NotNull
	private String unlock_key;
	
	@NotNull
	private Date request_date;
	
	@NotNull
	private int attempts;

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public long getId_user() {
		return id_user;
	}

	public void setId_user(long id_user) {
		this.id_user = id_user;
	}

	public String getUnlock_key() {
		return unlock_key;
	}

	public void setUnlock_key(String unlock_key) {
		this.unlock_key = unlock_key;
	}

	public Date getRequest_date() {
		return request_date;
	}

	public void setRequest_date(Date request_date) {
		this.request_date = request_date;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	
}
