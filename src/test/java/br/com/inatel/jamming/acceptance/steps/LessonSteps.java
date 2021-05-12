package br.com.inatel.jamming.acceptance.steps;

import br.com.inatel.jamming.model.Instrument;
import br.com.inatel.jamming.model.Lesson;
import br.com.inatel.jamming.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LessonSteps {

	private Lesson lesson;
	
	@Given("A valid Lesson")
	public void a_valid_lesson() {
		User author = new User("Mage", "12312312312", "mage@email.com", "123456");
		Instrument instrument = new Instrument("Flute");
	    lesson = new Lesson("test Title", "Test Message", author, 200, instrument);
	}

	@Given("A User with credits")
	public void a_user_with_credits() {
	    User student = new User("Priest", "12312312312", "priest@email.com", "123456");
	    student.setCredits(500);
	}
	@When("A User buy a lesson")
	public void a_user_buy_a_lesson() {
	    
	}
	@Then("The lesson add the user as a student")
	public void the_lesson_add_the_user_as_a_student() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
}
