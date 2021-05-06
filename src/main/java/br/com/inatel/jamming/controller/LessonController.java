package br.com.inatel.jamming.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import br.com.inatel.jamming.controller.dto.LessonDetailsDto;
import br.com.inatel.jamming.controller.dto.LessonDto;
import br.com.inatel.jamming.controller.dto.UserDto;
import br.com.inatel.jamming.controller.form.LessonForm;
import br.com.inatel.jamming.controller.form.StudentForm;
import br.com.inatel.jamming.controller.form.UpdateLessonForm;
import br.com.inatel.jamming.controller.repository.LessonRepository;
import br.com.inatel.jamming.controller.repository.PostRepository;
import br.com.inatel.jamming.controller.repository.UserRepository;
import br.com.inatel.jamming.model.Lesson;
import br.com.inatel.jamming.model.User;

@RestController
@RequestMapping("/lessons")
public class LessonController {

	@Autowired
	private LessonRepository lessonRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@GetMapping
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
	public ResponseEntity<LessonDetailsDto> lessonDetails(@PathVariable Long lessonId) {
		Optional<Lesson> optional = lessonRepository.findById(lessonId);
		
		if(optional.isPresent()) {
			return ResponseEntity.ok(new LessonDetailsDto(optional.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<LessonDto> addLesson(@RequestBody @Valid LessonForm form, UriComponentsBuilder uriBuilder) {
		Lesson lesson = form.convert(userRepository);
		lessonRepository.save(lesson);
		
		URI uri = uriBuilder.path("/lessons/{id}").buildAndExpand(lesson.getId()).toUri();
		return ResponseEntity.created(uri).body(new LessonDto(lesson));
	}
	
	@PutMapping("/add/{lessonId}")
	public ResponseEntity<?> addStudent(@RequestBody @Valid StudentForm form, @PathVariable Long lessonId) {
		User user = form.convert(userRepository);
		Lesson lesson = lessonRepository.getOne(lessonId);
		
		if(user.getCredits() >= lesson.getPrice()) {
			System.out.println("entrou");
				user.subCredits(lesson.getPrice());
				lesson.getAuthor().addCredits(lesson.getPrice());
				user.addLesson(lesson);
				lesson.addStudent(user);
				lessonRepository.save(lesson);
				System.out.println(user.getCredits());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/{lessonId}")
	public ResponseEntity<LessonDto> updateLesson(@RequestBody @Valid UpdateLessonForm form, @PathVariable Long lessonId) {
		Optional<Lesson> optional = lessonRepository.findById(lessonId);
		
		if(optional.isPresent()) {
			Lesson lesson = form.update(optional.get());
			return ResponseEntity.ok(new LessonDto(lesson));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	@DeleteMapping("/{lessonId}")
	public ResponseEntity<?> deleteLesson(@PathVariable Long lessonId) {
		Optional<Lesson> optional = lessonRepository.findById(lessonId);
		
		if(optional.isPresent()) {
			lessonRepository.deleteById(lessonId);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
