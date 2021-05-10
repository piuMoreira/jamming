package br.com.inatel.jamming.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.inatel.jamming.controller.dto.PostDetailsDto;
import br.com.inatel.jamming.controller.dto.PostDto;
import br.com.inatel.jamming.controller.form.UpdatePostForm;
import br.com.inatel.jamming.model.Post;
import br.com.inatel.jamming.model.User;
import br.com.inatel.jamming.repository.PostRepository;
import br.com.inatel.jamming.repository.UserRepository;
import br.com.inatel.jamming.service.cloudinary.CloudinaryService;

@RestController
@RequestMapping("/posts")
public class PostsController {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CloudinaryService cloudinaryService;
	
	@GetMapping
	@Cacheable(value = "listPosts")
	public  List<PostDto> listPosts(String authorName) {
		System.out.println("entrou");
		if(authorName == null) {
			List<Post> posts = postRepository.findAll();
			return PostDto.convert(posts);
		} else {
			List<Post> posts = postRepository.findByAuthorName(authorName);
			return PostDto.convert(posts);
		}		
	}
	
	@GetMapping("/{postId}")	
	public ResponseEntity<PostDetailsDto> postDetails(@PathVariable Long postId) {
		Optional<Post> optional = postRepository.findById(postId);		
		if(optional.isPresent()) {
			return ResponseEntity.ok(new PostDetailsDto(optional.get()));
		}		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listPosts", allEntries = true)
	public ResponseEntity<PostDto> addPost(@RequestParam("title") String title, @RequestParam("message") String message,
								@RequestParam("userId") Long userId, UriComponentsBuilder uriBuilder,
								@RequestParam("file") MultipartFile file) throws IOException {
		
		String url = "";
		if (file != null) {
			Map uploadResult = cloudinaryService.upload(file, "image", "image");
			url = uploadResult.get("public_id").toString() + "." + uploadResult.get("format").toString();
		}
		
		User user = userRepository.getOne(userId);
		Post newPost = new Post(title, message, user, url);
		
		postRepository.save(newPost);
		URI uri = uriBuilder.path("/posts/{id}").buildAndExpand(newPost.getId()).toUri();
		return ResponseEntity.created(uri).body(new PostDto(newPost));
	}
	
	@PatchMapping("/{postId}")
	@Transactional
	@CacheEvict(value = "listPosts", allEntries = true)
	public ResponseEntity<PostDto> updatePost(@RequestBody @Valid UpdatePostForm form, @PathVariable Long postId){
		Optional<Post> optional = postRepository.findById(postId);
		
		if(optional.isPresent()) {
			Post post = form.update(postId, postRepository);
			return ResponseEntity.ok(new PostDto(post));
		}
				
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{postId}")
	@Transactional
	@CacheEvict(value = "listPosts", allEntries = true)
	public ResponseEntity<?> deletePost(@PathVariable Long postId) {
		Optional<Post> optional = postRepository.findById(postId);
		
		if(optional.isPresent()) {
			postRepository.deleteById(postId);
			return ResponseEntity.ok().build();
		}
				
		return ResponseEntity.notFound().build();
	}
	
}
