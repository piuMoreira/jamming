package br.com.inatel.jamming.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.inatel.jamming.model.Post;
import br.com.inatel.jamming.model.User;
import br.com.inatel.jamming.repository.UserRepository;

public class PostForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String title;
	@NotNull @NotEmpty @Length(min = 10)
	private String message;
	@NotNull @NotEmpty
	private String authorId;
	
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
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthor(String authorId) {
		this.authorId = authorId;
	}
	public Post toPost(UserRepository userRepository) {
		Optional<User> optional = userRepository.findById(Long.parseLong(this.authorId));
		if(optional.isPresent()) {
			Post post = new Post(title, message, optional.get());
			return post;			
		} else {
			return null;
		}
	}
		
}
