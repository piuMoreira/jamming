package br.com.inatel.jamming.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inatel.jamming.model.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

	Page<Lesson> findByInstrumentName(String instrumentName, Pageable pagination);
	
}
