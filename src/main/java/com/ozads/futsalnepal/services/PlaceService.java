package com.ozads.futsalnepal.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozads.futsalnepal.response.PlaceResponse;

import com.ozads.futsalnepal.model.Place;

import com.ozads.futsalnepal.repository.PlaceRepository;

@Service
public class PlaceService {
	
	@Autowired
	PlaceRepository placeRepository;
	
	public List<PlaceResponse> listAllPlaces(){
		
		List<Place> place=placeRepository.findAll();
		List<PlaceResponse> placeResponses=new ArrayList<>();
		place.stream().forEach(u->{
			PlaceResponse response =new PlaceResponse();
			response.setId(u.getId());
			response.setPlace(u.getPlace());
			placeResponses.add(response);
		});
		return placeResponses;
	}
	

}
