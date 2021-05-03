package br.com.inatel.jamming.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.inatel.jamming.controller.repository.UserRepository;
import br.com.inatel.jamming.model.Post;

public class PostDetailsDto {
	private Long id;
	private String title;
	private String message;
	private LocalDateTime date;
	private String author;
	private List<CommentDto> comments;
	
	public PostDetailsDto(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.message = post.getMessage();
		this.date = post.getDate();
		this.author = post.getAuthor().getName();
		this.comments = new ArrayList<CommentDto>();
		this.comments.addAll(post.getComments().stream().map(CommentDto::new).collect(Collectors.toList()));
	}
	
	public Long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getMessage() {
		return message;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public String getAuthor() {
		return author;
	}
	public List<CommentDto> getComments() {
		return comments;
	}
	
}
