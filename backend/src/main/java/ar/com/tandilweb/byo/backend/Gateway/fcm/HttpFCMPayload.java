package ar.com.tandilweb.byo.backend.Gateway.fcm;

import java.util.List;

import com.google.gson.Gson;

public class HttpFCMPayload implements Fcmp {
	
	private FCMNotify notification;
	private FCMPayloadData data;
	private String to;
	private String priority;
	private String restricted_package_name;
	
	private List<String> registration_ids;
	private String condition;
	private Long time_to_live = 432000L;
	
	public HttpFCMPayload(){
		this.notification = new FCMNotify("Titulo Notificacion","Cuerpo Notificacion");
		this.data = new FCMPayloadData();
		//this.to = "/topic/topicExample";
		this.priority = "high";
		this.restricted_package_name = "";
	}
	
	public String getBody() {
		Gson gs = new Gson();
		return gs.toJson(this);
	}

	public String getTarget() {
		return to;
	}

	public void setTarget(String target) {
		this.to = target;
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

	public String getRestricted_package_name() {
		return restricted_package_name;
	}

	public void setRestricted_package_name(String restricted_package_name) {
		this.restricted_package_name = restricted_package_name;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public FCMPayloadData getData() {
		return data;
	}

	public void setData(FCMPayloadData data) {
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
