package br.com.inatel.jamming.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inatel.jamming.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByName(String name);
	
}
