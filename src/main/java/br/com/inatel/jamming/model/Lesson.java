package br.com.inatel.jamming.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Lesson extends Post {

	private Long price;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Instrument instrument;
	@ManyToMany(mappedBy = "lessons")
	private List<User> students = new ArrayList<User>();
	
	public Lesson(String title, String message, User author, long price, Instrument instrument) {
		super(title, message, author);
		this.price = price;
		this.instrument = instrument;
	}
	
	public Lesson() {}

	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public Instrument getInstrument() {
		return instrument;
	}
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
	public List<User> getStudents() {
		return students;
	}
	public void setStudents(List<User> students) {
		this.students = students;
	}
	public void addStudent(User user) {
		this.students.add(user);
	}
	
	
}
