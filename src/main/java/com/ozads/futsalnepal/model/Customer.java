package com.ozads.futsalnepal.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.ozads.futsalnepal.util.Status;

@SuppressWarnings("serial")
@Entity
@Table(name="customer")
public class Customer implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="full_name")
	private String fullName;
	
	private String email;
	
	@Column(name="phone_no")
	private String phoneNo;
	
	private String address;
	
	@OneToMany(mappedBy="customer", fetch=FetchType.LAZY)
	private List<Login> logins;
	
	@OneToMany(mappedBy="customer", fetch=FetchType.LAZY)
	private List<Booking> bookings;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;
	private String password;

	private String username;
	
	

	public Customer() {
		super();
		
	}


	public Customer(Long customerId) {

	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}


			


	


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public List<Login> getLogins() {
		return logins;
	}


	public void setLogins(List<Login> logins) {
		this.logins = logins;
	}


	public List<Booking> getBookings() {
		return bookings;
	}


	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public Date getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
