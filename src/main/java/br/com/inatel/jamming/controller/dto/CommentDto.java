package br.com.inatel.jamming.controller.dto;

import java.time.LocalDateTime;

import br.com.inatel.jamming.model.Comment;

public class CommentDto {
	private Long id;
	private String author;
	private String message;
	private LocalDateTime date;
	
	public CommentDto(Comment comment) {
		this.id = comment.getId();
		this.author = comment.getAuthor().getName();
		this.message = comment.getMessage();
		this.date = comment.getDate();
	}
	
	public Long getId() {
		return id;
	}
	public String getAuthor() {
		return author;
	}
	public String getMessage() {
		return message;
	}
	public LocalDateTime getDate() {
		return date;
	}
}
