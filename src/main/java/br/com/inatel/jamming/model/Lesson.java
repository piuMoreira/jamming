package br.com.inatel.jamming.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Lesson extends Post {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal price;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Instrument instrument;
	@ManyToMany(mappedBy = "lessons")
	private List<User> students = new ArrayList<User>();
	
	public Lesson(String title, String message, User author, BigDecimal price, Instrument instrument) {
		super(title, message, author);
		this.price = price;
		this.instrument = instrument;
	}
	
	public Lesson() {}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
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
