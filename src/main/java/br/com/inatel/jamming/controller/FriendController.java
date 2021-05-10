package br.com.inatel.jamming.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inatel.jamming.controller.dto.UserDto;
import br.com.inatel.jamming.controller.form.FriendForm;
import br.com.inatel.jamming.model.User;
import br.com.inatel.jamming.repository.UserRepository;

@RestController
@RequestMapping("/friends")
public class FriendController {

	@Autowired
	private UserRepository userRepository;
	
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<UserDto>> listFriends(@PathVariable Long userId) {
		Optional<User> optional = userRepository.findById(userId);
		if(optional.isPresent()) {
			return ResponseEntity.ok(UserDto.convert(optional.get().getFriends()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/{userId}")
	@Transactional
	public ResponseEntity<UserDto> addFriend(@PathVariable Long userId, @RequestBody @Valid FriendForm form){
		Optional<User> optional = userRepository.findById(userId);
		if(optional.isPresent()) {
			User user = form.convert(userRepository);
			optional.get().addFriend(user);
			user.addFriend(optional.get());
			return ResponseEntity.ok(new UserDto(user));
		}
		return ResponseEntity.notFound().build();		
	}
	
	@DeleteMapping("/{userId}/{friendId}")
	@Transactional
	public ResponseEntity<?> deleteFriend(@PathVariable Long userId, @PathVariable Long friendId) {
		Optional<User> optional1 = userRepository.findById(userId);
		Optional<User> optional2 = userRepository.findById(friendId);
		if(optional1.isPresent() && optional2.isPresent()) {
			optional1.get().removeFriend(optional2.get());
			optional2.get().removeFriend(optional1.get());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.ok().build();
	}
}
