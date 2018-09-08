package com.ozads.futsalnepal.response;

import java.io.Serializable;


@SuppressWarnings("serial")
public class AddressResponseDto implements Serializable{
	
	private Long id;

	private String place;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	
	
	
	
	
	
	
	
}
