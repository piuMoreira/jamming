package br.com.inatel.jamming.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
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
@Profile("prod")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity<TokenDto> auth(@RequestBody @Valid LoginForm form) {
		UsernamePasswordAuthenticationToken loginData = form.toToken();
		try {
			Authentication authenticate = authManager.authenticate(loginData);
			String token = tokenService.generateToken(authenticate);
			return ResponseEntity.ok(new TokenDto(token, "Bearer","200"));			
		} 
		catch (AuthenticationException e) {
			return ResponseEntity.status(401).build();
		}
	}
	
}
