package com.ozads.futsalnepal.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@SuppressWarnings("serial")
public class BookingResponseDto implements Serializable{

	private Long id;
	
	private String timeSlot;
	private String bookingName;
	private Date bookingDate;
	private String bookingBy;
	private String bookingTo;

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
	public String getBookingBy() {
		return bookingBy;
	}
	public void setBookingBy(String bookingBy) {
		this.bookingBy = bookingBy;
	}
	public String getBookingTo() {
		return bookingTo;
	}
	public void setBookingTo(String bookingTo) {
		this.bookingTo = bookingTo;
	}
	
	
	
	
	
}
