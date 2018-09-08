package com.ozads.futsalnepal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozads.futsalnepal.model.Login;
import com.ozads.futsalnepal.util.Status;

public interface LoginRepository extends JpaRepository<Login,Long> {

	
	
	
	
	
	Login findLoginById(Long userId);
	
	Login findLoginByEmailAndStatusNot(String email, Status delete);
	
	Login findLoginByEmailAndStatus(String email, Status blocked);
	
	
	
	Login findByToken(String token);
	
	Login findByIdAndToken(Long loginId, String token);

	

	Login findByEmail(String email);

	Login findLoginByUsernameAndStatusNot(String username, Status deleted);

	Login findByUsernameAndStatus(String username, Status blocked);

	Login findByUsernameAndStatusNot(String username, Status deleted);

	Login findLoginByUsername(String username);

	Login findByUsername(String username);
}
