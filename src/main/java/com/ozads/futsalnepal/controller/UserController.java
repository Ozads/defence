package com.ozads.futsalnepal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ozads.futsalnepal.dto.UserDto;

import com.ozads.futsalnepal.request.UserCreationRequest;

import com.ozads.futsalnepal.response.UserResponseDto;
import com.ozads.futsalnepal.services.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value="/api/v1/user")
public class UserController {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	@ApiOperation(value="Create User",notes="Api to create user")
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Object> saveUser(@Validated @RequestBody UserCreationRequest userDto) {
		LOG.debug("Request for user creation in accepted");
		userService.saveUser(userDto);
		LOG.debug("user is created");
		return new ResponseEntity<Object>("User Created",HttpStatus.CREATED);
	}
	

	

	
	
}	


