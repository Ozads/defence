package com.ozads.futsalnepal.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@SuppressWarnings("serial")
public class BookingCreatationRequest implements Serializable {

	@JsonIgnore
	private Long id;
	
	private Long slotId;
	
	private Long courtId;
	
	
	
	
	
	public Long getCourtId() {
		return courtId;
	}
	public void setCourtId(Long courtId) {
		this.courtId = courtId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSlotId() {
		return slotId;
	}
	public void setSlotId(Long slotId) {
		this.slotId = slotId;
	}
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	

}
