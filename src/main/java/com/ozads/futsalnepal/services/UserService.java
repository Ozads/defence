package com.ozads.futsalnepal.services;

import java.security.NoSuchAlgorithmException;

import java.util.Date;


import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ozads.futsalnepal.exceptions.AlreadyExistException;
import com.ozads.futsalnepal.model.Login;
import com.ozads.futsalnepal.model.User;
import com.ozads.futsalnepal.repository.LoginRepository;
import com.ozads.futsalnepal.repository.UserRepository;

import com.ozads.futsalnepal.request.UserCreationRequest;


import com.ozads.futsalnepal.util.LoginStatus;
import com.ozads.futsalnepal.util.LoginType;
import com.ozads.futsalnepal.util.Md5Hashing;
import com.ozads.futsalnepal.util.Status;
import com.ozads.futsalnepal.util.UserRole;


@Service
public class UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	@Autowired
	UserRepository userRepository;

	@Autowired
	LoginRepository loginRepository;

	@Autowired
	LoginService loginService;

	
	@Transactional
	public User saveUser(UserCreationRequest userDto) {
		LOG.debug("User Creation...");
		Login l = loginRepository.findByUsername(userDto.getUsername());
		if (l != null ) {
			throw new AlreadyExistException("Username Already Exits");
		}

		User u = userRepository.findByEmailAndStatusNot(userDto.getEmail(), Status.DELETED);

		if (u != null) {
			throw new AlreadyExistException("Email already Exits !!");
		}
		User user = new User();
		user.setFullName(userDto.getFullName());
		user.setEmail(userDto.getEmail());
		
		user.setPhoneNo(userDto.getPhoneNo());
		user.setCreatedDate(new Date());
		user.setUsername(userDto.getUsername());
		
		user.setUserRole(UserRole.ADMIN);
		user.setStatus(Status.ACTIVE);

		LOG.debug("User Adding");
		User savedUser = userRepository.save(user);
		LOG.debug("User Added");

		if (user != null) {
			Login login = new Login();

			
			try {
				login.setPassword(Md5Hashing.getPw(userDto.getPassword()));
				login.setLoginStatus(LoginStatus.LOGOUT);
				login.setCreatedDate(new Date());
				login.setUsername(userDto.getUsername());
				login.setEmail(userDto.getEmail());
				login.setUser(savedUser);
				login.setLoginType(LoginType.ADMIN);
				login.setStatus(Status.ACTIVE);
				loginService.saveLogin(login);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			

		}

		return user;
	}

	
	
	

	private void emailDuplication(String email, User user) {

		User u = userRepository.findByEmailAndStatusNot(email, Status.DELETED);
		if (u != null && user.getId().equals(u.getId())) {

			throw new AlreadyExistException("Email Already Exit");

		}
	}

	

}
