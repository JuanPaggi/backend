package ar.com.tandilweb.byo.backend.Presentation.dto.out;

public class Notification {
	
	public enum Types {
		SOLICITUD_ENVIADA,
		SOLICITUD_RECIBIDA
	}
	
	public Types tipo;

}
