package br.com.inatel.jamming.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.inatel.jamming.model.Instrument;
import br.com.inatel.jamming.model.Lesson;
import br.com.inatel.jamming.model.User;
import br.com.inatel.jamming.repository.UserRepository;

public class LessonForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String title;
	@NotNull @NotEmpty @Length(min = 10)
	private String message;
	@NotNull @NotEmpty @Length(min = 5)
	private String author;
	@NotNull @NotEmpty
	private String price;
	@NotNull @NotEmpty
	private String instrument;
	
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getInstrument() {
		return instrument;
	}
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	public Lesson convert(UserRepository userRepository) {
		Optional<User> user = userRepository.findByName(author);
		if(user.isPresent()) {
			long price = Long.parseLong(this.price);
			Instrument instrument = new Instrument(this.instrument);
			
			Lesson lesson = new Lesson(title, message, user.get(), price, instrument);
			return lesson;
		}
		return null;
	}
	
	
}
