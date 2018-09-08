package com.ozads.futsalnepal.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ozads.futsalnepal.dto.LoginResponseDto;


@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("serial")
public class LoginResponse implements Serializable {

	private LoginResponseDto user;

	public LoginResponse(LoginResponseDto user) {
		super();
		this.user = user;
	}

	
	public LoginResponseDto getUser() {
		return user;
	}
	
	
	
	
}
