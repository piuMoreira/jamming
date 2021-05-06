package br.com.inatel.jamming.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.inatel.jamming.model.Lesson;

public class UpdateLessonForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String title;
	@NotNull @NotEmpty @Length(min = 10)
	private String message;
	@NotNull @NotEmpty
	private String price;
	
	public Lesson update(Lesson lesson) {
		Lesson lessonUpdate = lesson;
		lessonUpdate.setTitle(this.title);
		lessonUpdate.setMessage(this.message);
		lessonUpdate.setPrice(Long.parseLong(price));
		return lessonUpdate;
	}
	
}
