package br.com.inatel.jamming.controller.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.inatel.jamming.model.User;

public class UserDetailsDto {
	private Long id;	
	private String name;
	private String cellphone;
	private List<PostDto> posts = new ArrayList<PostDto>();
	private List<UserDto> friends = new ArrayList<UserDto>();
	private long credits;
	private List<LessonDto> boughtLessons = new ArrayList<LessonDto>();
	
	public UserDetailsDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.cellphone = user.getCellphone();
		this.posts = PostDto.convert(user.getPosts());
		this.friends = UserDto.convert(user.getFriends());
		this.credits = user.getCredits();
		this.boughtLessons = LessonDto.convert(user.getBoughtLessons());
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
	public long getCredits() {
		return credits;
	}
	public List<LessonDto> getboughtLessons() {
		return boughtLessons;
	}
	
	
	
}
