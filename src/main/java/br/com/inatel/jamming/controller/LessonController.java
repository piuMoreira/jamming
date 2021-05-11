package br.com.inatel.jamming.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.inatel.jamming.controller.dto.LessonDetailsDto;
import br.com.inatel.jamming.controller.dto.LessonDto;
import br.com.inatel.jamming.controller.form.LessonForm;
import br.com.inatel.jamming.controller.form.StudentForm;
import br.com.inatel.jamming.controller.form.UpdateLessonForm;
import br.com.inatel.jamming.model.Lesson;
import br.com.inatel.jamming.model.User;
import br.com.inatel.jamming.repository.LessonRepository;
import br.com.inatel.jamming.repository.UserRepository;

@RestController
@RequestMapping("/lessons")
public class LessonController {

	@Autowired
	private LessonRepository lessonRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@GetMapping
	@Cacheable(value = "listLessons")
	public List<LessonDto> listLessons(String instrumentName) {
		if(instrumentName == null) {
			List<Lesson> lessons = lessonRepository.findAll();
			return LessonDto.convert(lessons);
		} else {
			List<Lesson> lessons = lessonRepository.findByInstrumentName(instrumentName);
			return LessonDto.convert(lessons);
		}
	}
	
	@GetMapping("/{lessonId}")
	public ResponseEntity<LessonDetailsDto> lessonDetails(Authentication authentication, @PathVariable Long lessonId) {
		User authUser = (User) authentication.getPrincipal();
		User user = userRepository.getOne(authUser.getId());
		
		Optional<Lesson> optional = lessonRepository.findById(lessonId);
		
		if(optional.isPresent() && optional.get().getStudents().contains(user)) {
			return ResponseEntity.ok(new LessonDetailsDto(optional.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listLessons", allEntries = true)
	public ResponseEntity<LessonDto> addLesson(@RequestBody @Valid LessonForm form, UriComponentsBuilder uriBuilder) {
		Lesson lesson = form.toLesson(userRepository);
		lessonRepository.save(lesson);
		
		URI uri = uriBuilder.path("/lessons/{id}").buildAndExpand(lesson.getId()).toUri();
		return ResponseEntity.created(uri).body(new LessonDto(lesson));
	}
	
	@PatchMapping("/add/{lessonId}")
	@Transactional
	@CacheEvict(value = "listUsers", allEntries = true)
	public ResponseEntity<?> addStudent(@RequestBody @Valid StudentForm form, @PathVariable Long lessonId) {
		User user = form.toUser(userRepository);
		Lesson lesson = lessonRepository.getOne(lessonId);
		
		if(user.getCredits() >= lesson.getPrice()) {
				user.subCredits(lesson.getPrice());
				lesson.getAuthor().addCredits(lesson.getPrice());
				user.addBoughtLessons(lesson);
				lesson.addStudent(user);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PatchMapping("/{lessonId}")
	@Transactional
	@CacheEvict(value = "listUsers", allEntries = true)
	public ResponseEntity<LessonDto> updateLesson(@RequestBody @Valid UpdateLessonForm form, @PathVariable Long lessonId) {
		Optional<Lesson> optional = lessonRepository.findById(lessonId);
		
		if(optional.isPresent()) {
			Lesson lesson = form.update(optional.get());
			return ResponseEntity.ok(new LessonDto(lesson));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	@DeleteMapping("/{lessonId}")
	@Transactional
	@CacheEvict(value = "listUsers", allEntries = true)
	public ResponseEntity<?> deleteLesson(@PathVariable Long lessonId) {
		Optional<Lesson> optional = lessonRepository.findById(lessonId);
		
		if(optional.isPresent()) {
			lessonRepository.deleteById(lessonId);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
