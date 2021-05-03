package br.com.inatel.jamming.controller.dto;

import java.util.List;

import br.com.inatel.jamming.model.User;

public class UserDetailsDto {
	private Long id;	
	private String name;
	private String cellphone;
	private List<PostDto> posts;
	private List<UserDto> friends;
	private String instrument;
	
	public UserDetailsDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.cellphone = user.getCellphone();
		this.posts = PostDto.convert(user.getPosts());
		this.friends = UserDto.convert(user.getFriends());
		this.instrument = user.getInstrument().getName();
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
	public List<PostDto> getPosts() {
		return posts;
	}
	public List<UserDto> getFriends() {
		return friends;
	}
	public String getInstrument() {
		return instrument;
	}
	
	
}
