package br.com.inatel.jamming.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.inatel.jamming.controller.repository.UserRepository;
import br.com.inatel.jamming.model.Post;
import br.com.inatel.jamming.model.User;

public class PostForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String title;
	@NotNull @NotEmpty @Length(min = 10)
	private String message;
	@NotNull @NotEmpty
	private String author;
	
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Post convert(UserRepository userRepository) {
		User author = userRepository.findByName(this.author);
		Post post = new Post(title, message, author);
		return post;
	}
		
}
