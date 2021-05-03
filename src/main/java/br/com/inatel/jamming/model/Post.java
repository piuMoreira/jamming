package br.com.inatel.jamming.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Post {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String message;
	private LocalDateTime date;
	@ManyToOne
	private User author;
	@OneToMany(mappedBy = "post")
	private List<Comment> comments;
	
	@ElementCollection
	@JoinTable(name = "reactions", joinColumns = @JoinColumn(name ="user_ID"))
	@Column(name = "reaction",nullable = false)
	@Enumerated(EnumType.STRING)
	private Collection<Reaction> reactions;
	
	public Post(String title, String message, User author) {
		this.title = title;
		this.message = message;
		this.author = author;
		this.date = LocalDateTime.now();
	}
	
	public Post() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public Collection<Reaction> getReactions() {
		return reactions;
	}

	public void setReactions(Collection<Reaction> reactions) {
		this.reactions = reactions;
	}	
	
}
