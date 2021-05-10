package br.com.inatel.jamming.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.inatel.jamming.model.Post;
import br.com.inatel.jamming.repository.PostRepository;

public class UpdatePostForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String title;
	@NotNull @NotEmpty @Length(min = 10)
	private String message;
	
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
	

	public Post update(Long id, PostRepository postRepository) {		
			Post post = postRepository.getOne(id);
			post.setTitle(this.title);
			post.setMessage(this.message);
			return post;		
	}
	
}
