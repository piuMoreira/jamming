package br.com.inatel.jamming.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.inatel.jamming.model.User;

public class UserDto {

	private Long id;	
	private String name;
	private String cellphone;
	
	public UserDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.cellphone = user.getCellphone();
	}
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getCellphone() {
		return cellphone;
	}
	
	public static List<UserDto> convert(List<User> users) {
		return users.stream().map(UserDto::new).collect(Collectors.toList());
	}
	
	public static Page<UserDto> convertPage(Page<User> page) {
		return page.map(UserDto::new);
	}
	
}
