package br.com.inatel.jamming.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.inatel.jamming.controller.repository.UserRepository;
import br.com.inatel.jamming.model.User;

public class FriendForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String name;
	@NotNull @NotEmpty @Length(min = 11)
	private String cellphone;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public User convert(UserRepository userRepository) {
		Optional<User> optional = userRepository.findByName(name);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	
	
}
