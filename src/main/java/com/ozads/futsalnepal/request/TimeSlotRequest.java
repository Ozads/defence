package com.ozads.futsalnepal.request;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TimeSlotRequest implements Serializable {
	
	private String timeSlot;

	
	
	
	private String courtName;
	
	
	
	

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public String getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	
	
	
	
}
