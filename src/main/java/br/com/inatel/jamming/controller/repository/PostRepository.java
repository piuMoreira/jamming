package br.com.inatel.jamming.controller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inatel.jamming.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByAuthorName(String authorName);
	
}
