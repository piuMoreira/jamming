package br.com.inatel.jamming.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PostTest {

	private Post post;
	private User user;
	
	@Before
	public void beforeEach() {
		this.user = new User("Caster", "12345678900", "caster@email.com", "123456");
		this.post = new Post("Title for test","Message for test", user);
	}
	
	@Test
	public void shoudBeAbleToAddComment() {
		Comment comment = new Comment(user, "Comment for test", post);
		
		post.addComment(comment);
		Assert.assertSame(comment, post.getComments().get(0));
		Assert.assertEquals(1, post.getComments().size());
	}
	
	@Test
	public void shouldBeEmpty() {
		Assert.assertEquals(0, post.getComments().size());
	}

}
