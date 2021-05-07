package br.com.inatel.jamming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JammingApplication {

	public static void main(String[] args) {
		SpringApplication.run(JammingApplication.class, args);
	}

}
