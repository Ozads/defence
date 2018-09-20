package com.ozads.futsalnepal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class TimeSlot {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private Long id;
	
	private String timeSlot;
	
	private Long courtId;
	
	private String courtName;
	
	
	
	
	
	
	
	
	
	
	

	

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

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

	public String getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	

}
