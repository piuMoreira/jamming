package br.com.inatel.jamming.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inatel.jamming.controller.dto.PostDto;
import br.com.inatel.jamming.model.Post;
import br.com.inatel.jamming.model.User;

@RestController
@RequestMapping("/posts")
public class PostsController {
	
	@GetMapping
	public List<PostDto> listPosts() {
		Post post = new Post("My new flute", "Hi guys I have a new flute now!", new User("Piu", "40028922", "piu@email.com", "123"));
		
		return PostDto.convert(Arrays.asList(post,post,post));
	}
	
}
