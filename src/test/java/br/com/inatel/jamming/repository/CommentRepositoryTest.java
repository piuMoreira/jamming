package br.com.inatel.jamming.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.inatel.jamming.model.Comment;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

	@Autowired
	private CommentRepository commentRepository;
	
	@Test
	public void shouldFindCommentById() {
		Long commentId = 2L;
		Comment comment = commentRepository.findById(commentId).get();
		
		Assert.assertNotNull(comment);
		Assert.assertEquals(comment.getId(), commentId);
	}

}
