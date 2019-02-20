package ar.com.tandilweb.byo.backend.Model.domain;

public class Chats {
	
	private long id_user_requester;
	private long id_user_sender;
	private long last_message_id;
	
	public Chats(long id_user_requester, long id_user_sender, long last_message_id) {
		this.id_user_requester = id_user_requester;
		this.id_user_sender = id_user_sender;
		this.last_message_id = last_message_id;
	}
	
	public long getId_user_requester() {
		return id_user_requester;
	}
	public void setId_user_requester(long id_user_requester) {
		this.id_user_requester = id_user_requester;
	}
	public long getId_user_sender() {
		return id_user_sender;
	}
	public void setId_user_sender(long id_user_sender) {
		this.id_user_sender = id_user_sender;
	}
	public long getLast_message_id() {
		return last_message_id;
	}
	public void setLast_message_id(long last_message_id) {
		this.last_message_id = last_message_id;
	}
	
}
