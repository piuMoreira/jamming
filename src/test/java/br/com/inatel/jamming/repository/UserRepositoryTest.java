package br.com.inatel.jamming.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.inatel.jamming.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void shouldFindUserByEmail() {
		String email = "archer@email.com";
		
		User user = userRepository.findByEmail(email).get();
		
		Assert.assertNotNull(user);
		Assert.assertEquals(email, user.getEmail());
	}

}
