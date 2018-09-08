package com.ozads.futsalnepal.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@SuppressWarnings("serial")
@Entity
public class CourtAddress implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String place;
	

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


	public Court getCourt() {
		return court;
	}


	public void setCourt(Court court) {
		this.court = court;
	}

	

}
