package ar.com.tandilweb.byo.backend.Gateway.fcm;

public class FCMPayloadData {
	public String title;
	public String message;
	public String count;
	
	public FCMPayloadData(String title, String body) {
		this.title = title;
		this.message = body;
		this.count = "5";
	}
}
