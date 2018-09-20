package com.ozads.futsalnepal.dto;

import java.io.Serializable;


@SuppressWarnings("serial")
public class TimeSlotByCourtDto implements Serializable {
	
	private Long id;
	private String timeSlot;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	

}
