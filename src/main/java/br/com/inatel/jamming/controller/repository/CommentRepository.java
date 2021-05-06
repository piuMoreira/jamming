package br.com.inatel.jamming.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inatel.jamming.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Comment findByAuthorName(String authorName);
	
}
