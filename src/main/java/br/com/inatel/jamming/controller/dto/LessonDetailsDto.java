package br.com.inatel.jamming.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.inatel.jamming.model.Lesson;

public class LessonDetailsDto {

	private Long id;
	private String title;
	private String message;
	private String instrumentName;
	private LocalDateTime date;
	private String author;
	private BigDecimal price;
	
	public LessonDetailsDto(Lesson lesson) {
		this.id = lesson.getId();
		this.title = lesson.getTitle();
		this.message = lesson.getMessage();
		this.instrumentName = lesson.getInstrument().getName();
		this.date = lesson.getDate();
		this.author = lesson.getAuthor().getName();
		this.price = lesson.getPrice();
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

	public BigDecimal getPrice() {
		return price;
	}
	
	
}
