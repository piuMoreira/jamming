package br.com.inatel.jamming.controller.form;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.sun.istack.NotNull;

import br.com.inatel.jamming.model.Comment;
import br.com.inatel.jamming.model.Post;
import br.com.inatel.jamming.model.User;
import br.com.inatel.jamming.repository.PostRepository;
import br.com.inatel.jamming.repository.UserRepository;

public class CommentForm {

	@NotNull @NotEmpty
	private String authorId;
	@NotNull @NotEmpty @Length(min = 10)
	private String message;
	@NotNull @NotEmpty
	private String postId;
	
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}

	public Comment toComment(UserRepository userRepository, PostRepository postRepository) {
		User user = userRepository.getOne(Long.parseLong(authorId));
		Post post = postRepository.getOne(Long.parseLong(postId));
		
		Comment comment = new Comment(user, message, post);
		return comment;
	}
	
	
	
}
