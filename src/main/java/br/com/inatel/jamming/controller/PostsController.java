package br.com.inatel.jamming.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.inatel.jamming.controller.dto.PostDetailsDto;
import br.com.inatel.jamming.controller.dto.PostDto;
import br.com.inatel.jamming.controller.form.PostForm;
import br.com.inatel.jamming.controller.form.UpdatePostForm;
import br.com.inatel.jamming.controller.repository.PostRepository;
import br.com.inatel.jamming.controller.repository.UserRepository;
import br.com.inatel.jamming.model.Post;

@RestController
@RequestMapping("/posts")
public class PostsController {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public List<PostDto> listPosts(String authorName) {
		if(authorName == null) {
			List<Post> posts = postRepository.findAll();
			return PostDto.convert(posts);
		} else {
			List<Post> posts = postRepository.findByAuthorName(authorName);
			return PostDto.convert(posts);
		}		
	}
	
	@PostMapping
	public ResponseEntity<PostDto> addPost(@RequestBody @Valid PostForm form, UriComponentsBuilder uriBuilder) {
		Post newPost = form.convert(userRepository);
		postRepository.save(newPost);
		
		URI uri = uriBuilder.path("/posts/{id}").buildAndExpand(newPost.getId()).toUri();
		return ResponseEntity.created(uri).body(new PostDto(newPost));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDetailsDto> postDetails(@PathVariable Long id) {
		Optional<Post> optional = postRepository.findById(id);
		
		if(optional.isPresent()) {
			return ResponseEntity.ok(new PostDetailsDto(optional.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<PostDto> updatePost(@RequestBody @Valid UpdatePostForm form, @PathVariable Long id){
		Optional<Post> optional = postRepository.findById(id);
		
		if(optional.isPresent()) {
			Post post = form.update(id, postRepository);
			return ResponseEntity.ok(new PostDto(post));
		}
				
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletePost(@PathVariable Long id) {
		Optional<Post> optional = postRepository.findById(id);
		
		if(optional.isPresent()) {
			postRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
				
		return ResponseEntity.notFound().build();
	}
	
}
