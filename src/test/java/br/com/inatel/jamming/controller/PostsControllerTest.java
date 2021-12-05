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
public class PostsControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void shouldListPosts() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/posts"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string(containsString("id")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("title")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("message")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("date")));
	}
	
	@Test	
	public void shouldDeleteAPost() throws Exception {		
		URI uri = new URI("/posts/1/");
		
		mockMvc.perform(
				MockMvcRequestBuilders.delete(uri))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void shouldAddComment() throws Exception {
		URI uri = new URI("/posts/comment");
		String json = "{ \"authorId\":\"1\" ,"
						+ " \"message\":\"test message\" ,"
						+ " \"postId\":\"2\" }";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.content().string(containsString("id")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("author")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("message")))
			.andExpect(MockMvcResultMatchers.content().string(containsString("date")));
	}
	
	@Test
	public void shouldDeleteComment() throws Exception {
		URI uri = new URI("/posts/comment/1");
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.delete(uri))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
