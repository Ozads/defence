package com.ozads.futsalnepal.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.ozads.futsalnepal.response.PlaceResponse;
import com.ozads.futsalnepal.services.PlaceService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="api/v1/address")
public class PlaceController {

	private static final Logger LOG = LoggerFactory.getLogger(PlaceController.class);
		@Autowired
		PlaceService placeService;
		
		@RequestMapping(value="/listPlaces",method=RequestMethod.GET)
		@ApiOperation(value="List All Places",notes="Api to List all places")
		public ResponseEntity<Object> listAllPlaces(){
		LOG.debug("request Accepted to List All Address");
			List<PlaceResponse> placeResponse=placeService.listAllPlaces();

		return new ResponseEntity<Object>(placeResponse,HttpStatus.OK);
		}
}
