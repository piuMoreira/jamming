package br.com.inatel.jamming.controller;

import java.net.URI;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.inatel.jamming.controller.dto.UserDetailsDto;
import br.com.inatel.jamming.controller.dto.UserDto;
import br.com.inatel.jamming.controller.form.UpdateUserInfoForm;
import br.com.inatel.jamming.controller.form.UserForm;
import br.com.inatel.jamming.model.User;
import br.com.inatel.jamming.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	@Cacheable(value = "listUsers")
	public Page<UserDto> listUsers(@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable pagination) {
			Page<User> users = userRepository.findAll(pagination);
			return UserDto.convertPage(users);
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
		User newUser = form.toUser();	
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
	
	@GetMapping("/friend/{userId}")
	public ResponseEntity<List<UserDto>> listFriends(@PathVariable Long userId) {
		Optional<User> optional = userRepository.findById(userId);
		if(optional.isPresent()) {
			return ResponseEntity.ok(UserDto.convert(optional.get().getFriends()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/friend/{userId}")
	@Transactional
	public ResponseEntity<UserDto> addFriend(Authentication authentication,@PathVariable Long userId){
		
		User authUser = (User) authentication.getPrincipal();
		User user = userRepository.getOne(authUser.getId());
		Optional<User> optional = userRepository.findById(userId);
		if(optional.isPresent()) {
			optional.get().addFriend(user);
			user.addFriend(optional.get());
			return ResponseEntity.ok(new UserDto(user));
		}
		return ResponseEntity.notFound().build();		
	}
	
	@DeleteMapping("/friend/{userId}")
	@Transactional
	public ResponseEntity<?> deleteFriend(Authentication authentication, @PathVariable Long userId) {
		
		User authUser = (User) authentication.getPrincipal();
		User user = userRepository.getOne(authUser.getId());		
		Optional<User> optional = userRepository.findById(userId);
		
		if(optional.isPresent() && user.getFriends().contains(optional.get())) {
			user.removeFriend(optional.get());
			optional.get().removeFriend(user);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
