package br.com.inatel.jamming.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.inatel.jamming.model.User;
import br.com.inatel.jamming.repository.UserRepository;

public class FriendForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String userId;
	@NotNull @NotEmpty @Length(min = 11)
	private String cellphone;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public User toUser(UserRepository userRepository) {
		Optional<User> optional = userRepository.findById(Long.parseLong(userId));
		if(optional.isPresent()) {
			return optional.get();
		}
		throw new UsernameNotFoundException("User not found!");
	}
	
	
	
}
