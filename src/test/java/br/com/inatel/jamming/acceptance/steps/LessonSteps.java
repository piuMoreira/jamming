package br.com.inatel.jamming.acceptance.steps;

import org.junit.Assert;

import br.com.inatel.jamming.model.Instrument;
import br.com.inatel.jamming.model.Lesson;
import br.com.inatel.jamming.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LessonSteps {

	private Lesson lesson;
	private User student;
	private User author;
	
	@Given("A valid Lesson")
	public void a_valid_lesson() {
		author = new User("Mage", "12312312312", "mage@email.com", "123456");
		Instrument instrument = new Instrument("Flute");
	    lesson = new Lesson("test Title", "Test Message", author, 200, instrument);
	}

	@Given("A User with credits")
	public void a_user_with_credits() {
	    student = new User("Priest", "12312312312", "priest@email.com", "123456");
	    student.setCredits(500);
	}
	
	@When("A User buy a lesson")
	public void a_user_buy_a_lesson() {
	    student.buyLesson(lesson);
	}
	
	@Then("The lesson and the student have a relationship")
	public void the_lesson_and_the_student_have_a_relationship() {
	    Assert.assertNotEquals(0, lesson.getStudents().size());
	    Assert.assertNotEquals(0, student.getBoughtLessons().size());
	    Assert.assertEquals(300, student.getCredits());
	    Assert.assertEquals(200, author.getCredits());
	}
	
}
