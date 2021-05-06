package br.com.inatel.jamming.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Instrument {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private String name;
	private String key;
	private String family;
//	@ManyToMany(mappedBy = "instruments")
//	private List<User> players;
	
	public Instrument(String name, String key, String family) {
		this.name = name;
		this.key = key;
		this.family = family;
	}
	
	public Instrument(String name) {
		super();
		this.name = name;
	}

	public Instrument() {}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setClave(String key) {
		this.key = key;
	}
	public String getFamily() {
		return family;
	}
	public void setType(String family) {
		this.family = family;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setFamily(String family) {
		this.family = family;
	}	
	
	
}
