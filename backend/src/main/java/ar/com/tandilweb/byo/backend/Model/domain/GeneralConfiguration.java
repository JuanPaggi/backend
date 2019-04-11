package ar.com.tandilweb.byo.backend.Model.domain;

public class GeneralConfiguration {

		private long id;
		private String clave;
		private String valor;
		
		public GeneralConfiguration(long id, String clave, String valor) {
			this.id = id;
			this.clave = clave;
			this.valor = valor;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getClave() {
			return clave;
		}
		public void setClave(String clave) {
			this.clave = clave;
		}
		public String getValor() {
			return valor;
		}
		public void setValor(String valor) {
			this.valor = valor;
		}
	
	
	
	
	
}
