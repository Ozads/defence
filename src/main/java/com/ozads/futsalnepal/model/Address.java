package com.ozads.futsalnepal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String place;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	

	@ManyToOne
	@JoinColumn(name = "court_id")
	private Court court;


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
