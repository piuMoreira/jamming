package br.com.inatel.jamming.controller;


import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.inatel.jamming.controller.dto.CommentDto;
import br.com.inatel.jamming.controller.form.CommentForm;
import br.com.inatel.jamming.controller.repository.CommentRepository;
import br.com.inatel.jamming.controller.repository.PostRepository;
import br.com.inatel.jamming.controller.repository.UserRepository;
import br.com.inatel.jamming.model.Comment;

@RestController
@RequestMapping("/comments")
public class CommentController {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping
	public ResponseEntity<CommentDto> addCommentToPost(@RequestBody @Valid CommentForm form, UriComponentsBuilder uriBuilder) {
			Comment comment = form.convert(userRepository, postRepository);
			comment.getPost().addComment(comment);
			commentRepository.save(comment);
			
			URI uri = uriBuilder.path("/comments/{id}").buildAndExpand(comment.getId()).toUri();
			return ResponseEntity.created(uri).body(new CommentDto(comment));
		
	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
		Optional<Comment> optional = commentRepository.findById(commentId);
		
		if(optional.isPresent()) {
			commentRepository.delete(optional.get());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
