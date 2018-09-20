package com.ozads.futsalnepal.request;

import java.io.Serializable;
import java.util.List;



@SuppressWarnings("serial")
public class CustomerCreationRequest implements Serializable {
	
	private String fullName;
	
	private String email;
	private String phoneNo;
	private String username;
	private String password;
	
	private String address;
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

	


	
	
	
	
	
	
	

}
