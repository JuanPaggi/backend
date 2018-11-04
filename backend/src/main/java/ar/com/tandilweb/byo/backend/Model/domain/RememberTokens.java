package ar.com.tandilweb.byo.backend.Model.domain;

import java.util.Date;

public class RememberTokens {
	
	private long id_user;
	private String unlock_key;
	private Date request_date;
	private int attempts;
	
	public RememberTokens(long id_user, String unlock_key, Date request_date, int attempts) {
		this.id_user = id_user;
		this.unlock_key = unlock_key;
		this.request_date = request_date;
		this.attempts = attempts;
	}
	
	public RememberTokens() {
		
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
