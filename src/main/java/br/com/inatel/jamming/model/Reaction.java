package br.com.inatel.jamming.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Reaction {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private React react;
	@ManyToOne
	private User author;
	@ManyToOne
	private Post post;
	
	public Reaction(User author, Post post) {
		this.react = React.LIKED;
		this.author = author;
		this.post = post;
	}
	
	public Reaction(React react ,User author, Post post) {
		this.react = react;
		this.author = author;
		this.post = post;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public React getReact() {
		return react;
	}
	
	public void reactAsLiked() {
		this.react = React.LIKED;
	}
	
	public void reactAsLoved() {
		this.react = React.LOVED;
	}
	
	public void reactAsInteresting() {
		this.react = React.INTERESTING;
	}
	
	public void reactAsCringe() {
		this.react = React.CRINGE;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	
	
	
}
