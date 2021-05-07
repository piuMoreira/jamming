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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.inatel.jamming.controller.dto.UserDetailsDto;
import br.com.inatel.jamming.controller.dto.UserDto;
import br.com.inatel.jamming.controller.form.UpdateUserInfoForm;
import br.com.inatel.jamming.controller.form.UserForm;
import br.com.inatel.jamming.controller.repository.UserRepository;
import br.com.inatel.jamming.model.User;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	@Cacheable(value = "listUsers")
	public List<UserDto> listUsers() {
			List<User> users = userRepository.findAll();
			return UserDto.convert(users);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDetailsDto> searchUser(@PathVariable Long userId) {
		Optional<User> optional = userRepository.findById(userId);
		if(optional.isPresent()) {
			UserDetailsDto userDetails = new UserDetailsDto(optional.get());
			return ResponseEntity.ok(userDetails);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listUsers", allEntries = true)
	public ResponseEntity<UserDto> addUser(@RequestBody @Valid UserForm form, UriComponentsBuilder uriBuilder) {
		User newUser = form.convert();		
		userRepository.save(newUser);
		URI uri = uriBuilder.path("/users/{userId}").buildAndExpand(newUser.getId()).toUri();
		return ResponseEntity.created(uri).body(new UserDto(newUser));
	}
	
	@PatchMapping("/{userId}")
	@Transactional
	@CacheEvict(value = "listUsers", allEntries = true)
	public ResponseEntity<UserDto> updateUSer(@RequestBody @Valid UpdateUserInfoForm form, @PathVariable Long userId) {
		Optional<User> optional = userRepository.findById(userId);
		
		if(optional.isPresent()) {
			User user = form.update(userId, userRepository);
			return ResponseEntity.ok(new UserDto(user));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{userId}")
	@Transactional
	@CacheEvict(value = "listUsers", allEntries = true)
	public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
		Optional<User> optional = userRepository.findById(userId);
		
		if(optional.isPresent()) {
			userRepository.deleteById(userId);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
