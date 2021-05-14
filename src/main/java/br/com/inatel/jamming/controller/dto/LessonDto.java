package br.com.inatel.jamming.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.inatel.jamming.model.Lesson;

public class LessonDto {
	private Long id;
	private String title;
	private String instrumentName;
	private LocalDateTime date;
	
	public LessonDto(Lesson lesson) {
		this.id = lesson.getId();
		this.title = lesson.getTitle();
		this.instrumentName = lesson.getInstrument().getName();
		this.date = lesson.getDate();
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getInstrumentName() {
		return instrumentName;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public static List<LessonDto> convert(List<Lesson> lessons) {
		return lessons.stream().map(LessonDto::new).collect(Collectors.toList());
	}
	
	public static Page<LessonDto> convertPage(Page<Lesson> page) {
		return page.map(LessonDto::new);
	}
	
	
}
