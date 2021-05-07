package br.com.inatel.jamming.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inatel.jamming.config.security.TokenService;
import br.com.inatel.jamming.controller.dto.TokenDto;
import br.com.inatel.jamming.controller.form.LoginForm;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity<TokenDto> auth(@RequestBody @Valid LoginForm form) {
		UsernamePasswordAuthenticationToken loginData = form.convert();
		try {
			Authentication authenticate = authManager.authenticate(loginData);
			String token = tokenService.generateToken(authenticate);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));			
		} 
		catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
}
