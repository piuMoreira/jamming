package br.com.inatel.jamming.controller;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

import br.com.inatel.jamming.controller.dto.LessonDetailsDto;
import br.com.inatel.jamming.controller.dto.LessonDto;
import br.com.inatel.jamming.controller.form.StudentForm;
import br.com.inatel.jamming.controller.form.UpdateLessonForm;
import br.com.inatel.jamming.model.Instrument;
import br.com.inatel.jamming.model.Lesson;
import br.com.inatel.jamming.model.User;
import br.com.inatel.jamming.repository.LessonRepository;
import br.com.inatel.jamming.repository.UserRepository;
import br.com.inatel.jamming.service.cloudinary.CloudinaryService;

@RestController
@RequestMapping("/lessons")
public class LessonController {

	@Autowired
	private LessonRepository lessonRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CloudinaryService cloudinaryService;
	
	
	@GetMapping
	@Cacheable(value = "listLessons")
	public Page<LessonDto> listLessons(String instrumentName,
					@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable pagination) {
		if(instrumentName == null) {
			Page<Lesson> lessons = lessonRepository.findAll(pagination);
			return LessonDto.convertPage(lessons);
		} else {
			Page<Lesson> lessons = lessonRepository.findByInstrumentName(instrumentName, pagination);
			return LessonDto.convertPage(lessons);
		}
	}
	
	@GetMapping("/{lessonId}")
	public ResponseEntity<LessonDetailsDto> lessonDetails(@PathVariable Long lessonId) {
		
		Optional<Lesson> optional = lessonRepository.findById(lessonId);
		
		if(optional.isPresent()) {
			return ResponseEntity.ok(new LessonDetailsDto(optional.get()));
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listLessons", allEntries = true)
	public ResponseEntity<LessonDto> addLesson(Authentication authentication, @RequestParam("title") String title,
						@RequestParam("message") String message,
						@RequestParam("price") String price, @RequestParam("instrumentName") String instrumentName,
				@RequestParam(value="file", required = false) MultipartFile file, UriComponentsBuilder uriBuilder) throws IOException {
		
		String annexUrl = "";
		if (file != null) {
			Map uploadResult = cloudinaryService.upload(file, "image", "image");
			annexUrl = uploadResult.get("public_id").toString() + "." + uploadResult.get("format").toString();
		}
		User authUser = (User) authentication.getPrincipal();
		User user = userRepository.getOne(authUser.getId());
		Instrument instrument = new Instrument(instrumentName);
		Lesson lesson = new Lesson(title, message, user, Long.parseLong(price), instrument);
		lesson.setAnnexUrl(annexUrl);
		lessonRepository.save(lesson);
		
		URI uri = uriBuilder.path("/lessons/{id}").buildAndExpand(lesson.getId()).toUri();
		return ResponseEntity.created(uri).body(new LessonDto(lesson));
	}
	
	@PatchMapping("/add/{lessonId}")
	@Transactional
	@CacheEvict(value = {"listUsers","listLessons"}, allEntries = true)
	public ResponseEntity<?> addStudent(@RequestBody @Valid StudentForm form, @PathVariable Long lessonId) {
		User user = form.toUser(userRepository);
		Lesson lesson = lessonRepository.getOne(lessonId);
		
		if(user.buyLesson(lesson)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PatchMapping("/{lessonId}")
	@Transactional
	@CacheEvict(value = "listLessons", allEntries = true)
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
	@CacheEvict(value = "listLessons", allEntries = true)
	public ResponseEntity<?> deleteLesson(@PathVariable Long lessonId) {
		Optional<Lesson> optional = lessonRepository.findById(lessonId);
		
		if(optional.isPresent()) {
			lessonRepository.deleteById(lessonId);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
