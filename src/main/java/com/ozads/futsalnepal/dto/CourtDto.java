package com.ozads.futsalnepal.dto;

import java.io.Serializable;
import java.util.List;



@SuppressWarnings("serial")
public class CourtDto implements Serializable {

	private Long id;
	private String courtName;
	private String phoneNo;
	private String username;
	private String email;
	private String price;
	
	String address;
	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourtName() {
		return courtName;
	}
	
	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}
	
	
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhoneNo() {
		return phoneNo;
	}
	
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	
	
	
}
