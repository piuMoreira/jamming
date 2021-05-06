package br.com.inatel.jamming.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.inatel.jamming.model.Lesson;
import br.com.inatel.jamming.model.User;

public class LessonDetailsDto {

	private Long id;
	private String title;
	private String message;
	private String instrumentName;
	private LocalDateTime date;
	private String author;
	private Long price;
	private List<CommentDto> comments;
	private List<UserDto> students;
	
	public LessonDetailsDto(Lesson lesson) {
		this.id = lesson.getId();
		this.title = lesson.getTitle();
		this.message = lesson.getMessage();
		this.instrumentName = lesson.getInstrument().getName();
		this.date = lesson.getDate();
		this.author = lesson.getAuthor().getName();
		this.price = lesson.getPrice();
		this.comments = new ArrayList<CommentDto>(); 
		this.comments.addAll(lesson.getComments().stream().map(CommentDto::new).collect(Collectors.toList()));
		this.students = new ArrayList<UserDto>();
		this.students.addAll(lesson.getStudents().stream().map(UserDto::new).collect(Collectors.toList()));
		
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

	public String getInstrumentName() {
		return instrumentName;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getAuthor() {
		return author;
	}

	public Long getPrice() {
		return price;
	}

	public List<CommentDto> getComments() {
		return comments;
	}

	public List<UserDto> getStudents() {
		return students;
	}
	
	
}
