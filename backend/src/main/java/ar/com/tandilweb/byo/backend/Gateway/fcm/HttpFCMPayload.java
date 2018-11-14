package ar.com.tandilweb.byo.backend.Gateway.fcm;

import java.util.List;

import com.google.gson.Gson;

public class HttpFCMPayload implements Fcmp {
	
	private String target;
	private List<String> registration_ids;
	private String condition;
	private String priority;
	private Object data;
	private Long time_to_live = 432000L;
	private FCMNotify notification = new FCMNotify("demo", "gg izi");

	public String getBody() {
		// TODO Auto-generated method stub
		Gson gs = new Gson();
		return gs.toJson(this);
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<String> getRegistration_ids() {
		return registration_ids;
	}

	public void setRegistration_ids(List<String> registration_ids) {
		this.registration_ids = registration_ids;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Long getTime_to_live() {
		return time_to_live;
	}

	public void setTime_to_live(Long time_to_live) {
		this.time_to_live = time_to_live;
	}

	public FCMNotify getNotification() {
		return notification;
	}

	public void setNotification(FCMNotify notification) {
		this.notification = notification;
	}
	
	

}
