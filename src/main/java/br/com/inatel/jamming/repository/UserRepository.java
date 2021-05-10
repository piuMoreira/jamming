package br.com.inatel.jamming.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inatel.jamming.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByName(String name);
	Optional<User> findByEmail(String email);
	
}
