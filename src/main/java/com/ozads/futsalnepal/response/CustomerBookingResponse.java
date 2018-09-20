package com.ozads.futsalnepal.response;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
public class CustomerBookingResponse implements Serializable {
	@JsonIgnore
	private Long id;
	
	private String timeSlot;
	private String bookingName;
	private Date bookingDate;
	private String bookingTo;
	
	
	String address;


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


	public String getBookingName() {
		return bookingName;
	}


	public void setBookingName(String bookingName) {
		this.bookingName = bookingName;
	}


	public Date getBookingDate() {
		return bookingDate;
	}


	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}


	public String getBookingTo() {
		return bookingTo;
	}


	public void setBookingTo(String bookingTo) {
		this.bookingTo = bookingTo;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}
	
	
	

}
