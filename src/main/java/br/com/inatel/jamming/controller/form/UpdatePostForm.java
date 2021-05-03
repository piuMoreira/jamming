package br.com.inatel.jamming.controller.form;

import br.com.inatel.jamming.controller.repository.PostRepository;
import br.com.inatel.jamming.model.Post;

public class UpdatePostForm {

	private String title;
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
