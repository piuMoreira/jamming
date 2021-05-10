package br.com.inatel.jamming.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.inatel.jamming.model.User;
import br.com.inatel.jamming.repository.UserRepository;

public class UpdateUserInfoForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String name;
	@NotNull @NotEmpty @Length(min = 11)
	private String cellphone;
	@NotNull @NotEmpty
	private String credits;
	
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
	public String getCredits() {
		return credits;
	}
	public void setCredits(String credits) {
		this.credits = credits;
	}
	public User update(Long id, UserRepository userRepository) {
		User user = userRepository.getOne(id);
		user.setName(name);
		user.setCellphone(cellphone);
		user.setCredits(Long.parseLong(credits));
		return user;
	}
	
	
	
}
