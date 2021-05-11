package br.com.inatel.jamming.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.inatel.jamming.model.Post;

public class PostDto {
	private Long id;
	private String title;
	private String message;
	private LocalDateTime date;
	
	public PostDto(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.message = post.getMessage();
		this.date = post.getDate();
		
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
	

	public static List<PostDto> convert(List<Post> list) {
		return list.stream().map(PostDto::new).collect(Collectors.toList());
	}
	
	
}
