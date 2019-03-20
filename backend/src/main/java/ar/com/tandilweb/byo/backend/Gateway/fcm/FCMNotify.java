package ar.com.tandilweb.byo.backend.Gateway.fcm;

public class FCMNotify {
	public String title;
	public String body;
	public String sound;
	public String click_action; 
	public String icon;
	
	public FCMNotify(String title, String body) {
		this.title = title;
		this.body = body;
		this.sound = "default";
		this.click_action = "FCM_PLUGIN_ACTIVITY";
		this.icon= "fcm_push_icon";
	}
}
