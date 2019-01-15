package ar.com.tandilweb.byo.backend.Model.domain;

import java.util.Calendar;

public class Friendships {
	private long id_user_requester;
	private long id_user_target;
	private boolean accepted;
	private Calendar date_emitted;
	private boolean viewed;
	private boolean rejected;
	
	public Friendships() {}
	
	public Friendships(long id_user_requester, long id_user_target, boolean is_accepted, Calendar date_emitted,
			boolean is_viewed) {
		this.id_user_requester = id_user_requester;
		this.id_user_target = id_user_target;
		this.accepted = is_accepted;
		this.date_emitted = date_emitted;
		this.viewed = is_viewed;
		
	}

	public long getId_user_requester() {
		return id_user_requester;
	}

	public void setId_user_requester(long id_user_requester) {
		this.id_user_requester = id_user_requester;
	}

	public long getId_user_target() {
		return id_user_target;
	}

	public void setId_user_target(long id_user_target) {
		this.id_user_target = id_user_target;
	}

	public boolean is_accepted() {
		return accepted;
	}

	public void setIs_accepted(boolean is_accepted) {
		this.accepted = is_accepted;
	}

	public Calendar getDate_emitted() {
		return date_emitted;
	}

	public void setDate_emitted(Calendar date_emitted) {
		this.date_emitted = date_emitted;
	}

	public boolean is_viewed() {
		return viewed;
	}

	public void setViewed(boolean is_viewed) {
		this.viewed = is_viewed;
	}
	
	public boolean is_rejected() {
		return this.rejected;
	}

	public void setRejected(boolean is_rejected) {
		this.rejected = is_rejected;
	}
	
}
