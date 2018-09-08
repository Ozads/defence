package com.ozads.futsalnepal.controller;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ozads.futsalnepal.dto.LoginDto;
import com.ozads.futsalnepal.dto.LoginResponseDto;

import com.ozads.futsalnepal.response.LoginResponse;
import com.ozads.futsalnepal.services.LoginService;
import com.ozads.futsalnepal.util.Status;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api/v1")
public class LoginController {

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(value="login Api",notes="Api to Login")
	public ResponseEntity<Object> login(@Valid @RequestBody LoginDto loginDto) {
		LOG.debug("Login Request", loginDto.getUsername());
		LoginResponseDto loginResponseDto = loginService.logInUser(loginDto.getUsername(), loginDto.getPassword(),
				Status.ACTIVE);
		return new ResponseEntity<Object>(new LoginResponse(loginResponseDto), HttpStatus.OK);
	}
	
	
	@ApiOperation(value="Logout",notes="Api to Logout")
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<Object> logout(@RequestHeader Long userId) {
		loginService.logout(userId);
		return new ResponseEntity<Object>("You are logged out from the system",HttpStatus.OK);
	}
	
	
	
	@ApiOperation(value="Check Token")
	@RequestMapping(value="/checkToken",method=RequestMethod.POST)
	public ResponseEntity<Object> checkToken(@RequestHeader String token){
		LOG.debug("Token checking");
		loginService.chekToken(token);
		return new ResponseEntity<Object>("Token is valid",HttpStatus.OK);
	}
	
}
