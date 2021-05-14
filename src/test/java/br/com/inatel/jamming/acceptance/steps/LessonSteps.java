package br.com.inatel.jamming.acceptance.steps;

import org.junit.Assert;

import br.com.inatel.jamming.model.Instrument;
import br.com.inatel.jamming.model.Lesson;
import br.com.inatel.jamming.model.User;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LessonSteps {

	private Lesson lesson;
	private User student;
	private User author;
	
	@Before
	public void setupEntities() {
		author = new User("Mage", "12312312312", "mage@email.com", "123456");
		Instrument instrument = new Instrument("Flute");
		lesson = new Lesson("test Title", "Test Message", author, 0, instrument);
		student = new User("Priest", "12312312312", "priest@email.com", "123456");
	}
	
	@Given("A valid Lesson with cost {long} and a User with {long} positive credits")
	public void a_valid_lesson_with_cost_and_a_user_with_positive_credits(long lessonCost, long userInitialCredits) {
		lesson.setPrice(lessonCost);
		student.setCredits(userInitialCredits);
	}

	@When("A User buy a lesson")
	public void a_user_buy_a_lesson() {
		student.buyLesson(lesson);
	}
	
	@Then("The lesson and the student should reference each other")
	public void the_lesson_and_the_student_should_reference_each_other() {
		Assert.assertNotEquals(0, lesson.getStudents().size());
		Assert.assertNotEquals(0, student.getBoughtLessons().size());
	}
	
	@Then("The Author of the lesson should have {long} credits")
	public void the_author_of_the_lesson_should_have_credits(long lessonCredits) {
		Assert.assertEquals(lessonCredits, author.getCredits());;
	}
	
	@Then("The User who bought the lesson should have {long}")
	public void the_user_who_bought_the_lesson_should_have(long userFinalCredits) {
		Assert.assertEquals(userFinalCredits, student.getCredits());
	}
	
}
