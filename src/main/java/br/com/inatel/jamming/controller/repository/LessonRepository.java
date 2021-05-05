package br.com.inatel.jamming.controller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inatel.jamming.model.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

	List<Lesson> findByInstrumentName(String instrumentName);
	
}
