package br.com.inatel.jamming.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inatel.jamming.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	Page<Post> findByAuthorName(String authorName, Pageable pagination);
	
}
