package br.com.inatel.jamming.controller.dto;

public class TokenDto {

	private String token;
	private String type;
	private String status;

	public TokenDto(String token, String type, String status) {
		this.token = token;
		this.type = type;
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public String getType() {
		return type;
	}

	public String getStatus() {
		return status;
	}
}
