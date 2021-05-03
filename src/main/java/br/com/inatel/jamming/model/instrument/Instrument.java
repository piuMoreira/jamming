package br.com.inatel.jamming.model.instrument;

public abstract class Instrument {

	private String name;
	private String key;
	private String family;
	
	public Instrument(String name, String clave, String type) {
		this.name = name;
		this.key = clave;
		this.family = type;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClave() {
		return key;
	}
	public void setClave(String clave) {
		this.key = clave;
	}
	public String getType() {
		return family;
	}
	public void setType(String type) {
		this.family = type;
	}	
	
}
