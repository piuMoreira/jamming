package br.com.inatel.jamming.controller;

import static org.hamcrest.CoreMatchers.containsString;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void shouldShowUserDetails() throws Exception {
		URI uri = new URI("/users/4");
		
		mockMvc.perform(MockMvcRequestBuilders
				.get(uri))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string(containsString("id")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("name")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("cellphone")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("posts")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("friends")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("credits")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("boughtLessons")));
	}
	
	@Test
	public void shouldCreateUser() throws Exception {
		URI uri = new URI("/users");
		String json = "{\"name\":\"Druid\","
					+ "\"cellphone\":\"00000000000\","
					+ "\"email\":\"druid@email.com\","
					+ "\"password\":\"123456\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isCreated());
		
		
	}

}
