package br.com.inatel.jamming.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.inatel.jamming.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value(value =  "${jamming.jwt.expiration}")
	private String expiration;
	
	@Value(value =  "${jamming.jwt.secret}")
	private String secret;
	
	public String generateToken(Authentication authenticate) {
		User principal = (User) authenticate.getPrincipal();
		Date today = new Date();
		Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
		return Jwts.builder()
				.setIssuer("Jamming API")
				.setSubject(principal.getId().toString())
				.setIssuedAt(today)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS256,  secret)
				.compact();
	}

}
