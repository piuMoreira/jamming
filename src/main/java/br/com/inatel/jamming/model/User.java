package br.com.inatel.jamming.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class User {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private String name;
	private String cellphone;	
	private String email;
	private String password;	
	@OneToMany(mappedBy = "author")
	private List<Post> posts;	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<User> friends;
	@ManyToMany
	@JoinTable(name = "user_instruments", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="instrument_id"))
	private List<Instrument> instruments = new ArrayList<Instrument>();
	
	public User(String name, String cellphone, String email, String password, String instrumentName) {
		this.name = name;
		this.cellphone = cellphone;
		this.email = email;
		this.password = password;
		this.instruments.add(new Instrument(instrumentName));
	}
	
	public User() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}	
	public List<Instrument> getInstruments() {
		return instruments;
	}

	public void setInstruments(List<Instrument> instrument) {
		this.instruments = instrument;
	}

	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	public void addPost(Post post) {
		this.posts.add(post);
	}
	public List<User> getFriends() {
		return friends;
	}
	public void setFriends(List<User> friends) {
		this.friends = friends;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	} 
	public void addFriend(User friend) {
		this.friends.add(friend);
	}
	
	
}
