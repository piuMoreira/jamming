package br.com.inatel.jamming.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "Users")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private String name;
	private String cellphone;
	@Column(unique = true)
	private String email;
	private String password;	
	private long credits;
	@OneToMany(mappedBy = "author")
	private List<Post> posts;	
	@OneToMany(mappedBy = "author")
	private List<Comment> comments;
	@ManyToMany(fetch = FetchType.EAGER)
	private List<User> friends;
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Lesson> lessons;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Profile> profiles = new ArrayList<>();
	
	public User(String name, String cellphone, String email, String password) {
		this.name = name;
		this.cellphone = cellphone;
		this.email = email;
		this.password = password;
		this.credits = 0;
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
	public void removeFriend(User user) {
		this.friends.remove(user);
	}

	public long getCredits() {
		return credits;
	}
	public void setCredits(long credits) {
		this.credits = credits;
	}
	public void addCredits(long credits) {
		this.credits += credits;
	}
	public void subCredits(long credits) {
		this.credits -= credits;
	}
	public List<Lesson> getLessons() {
		return lessons;
	}
	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}
	public void addLesson(Lesson lesson) {
		this.lessons.add(lesson);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.profiles;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
