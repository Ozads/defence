package com.ozads.futsalnepal.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ozads.futsalnepal.util.BookingStatus;

@SuppressWarnings("serial")
@Entity
@Table(name="booking")
public class Booking implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String price;
	
	@Column(name="booking_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date bookingDate;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	
	@ManyToOne
	@JoinColumn(name="court_id")
	private Court court;
	
	
	
	
	private String timeSlot;

	@Enumerated(EnumType.STRING)	
	private BookingStatus bookingStatus;
	
	


	
	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}


	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}


	

	

	


	

	


	public String getTimeSlot() {
		return timeSlot;
	}


	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public Date getBookingDate() {
		return bookingDate;
	}


	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public Court getCourt() {
		return court;
	}


	public void setCourt(Court court) {
		this.court = court;
	}
	
	
	
	

}
