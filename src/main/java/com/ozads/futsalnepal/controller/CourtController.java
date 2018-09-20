package com.ozads.futsalnepal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ozads.futsalnepal.dto.CourtDto;
import com.ozads.futsalnepal.request.CourtCreatationRequest;

import com.ozads.futsalnepal.response.CourtByAddressDto;
import com.ozads.futsalnepal.response.CourtResponseDto;
import com.ozads.futsalnepal.services.CourtService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value="/api/v1/court")
public class CourtController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CourtController.class);
	
	@Autowired
	private CourtService courtService;
	//@ApiImplicitParams({
		//@ApiImplicitParam(name="token",required=true,dataType="String",paramType="header")})
	@ApiOperation(value="Save Court",notes="Api to save court")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> saveCourt(
			@RequestBody CourtCreatationRequest courtCreatationRequest){
		LOG.debug("Request to Add court");
		courtService.saveCourt(courtCreatationRequest);
		
		return new ResponseEntity<Object>("Court Created",HttpStatus.CREATED);
		
	}
	//@ApiImplicitParams({
		//@ApiImplicitParam(name="token",required=true,dataType="String",paramType="header")})
	@ApiOperation(value="Delete Court",notes="Api to Delete Court")
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteCourt(@RequestHeader Long userId,@PathVariable ("id") Long id){
		  LOG.debug("Court Delete Request");
		  courtService.deleteCourt(userId,id);
		return new ResponseEntity<Object>("Court Reported",HttpStatus.OK);
	}
	
	//@ApiImplicitParams({
		//@ApiImplicitParam(name="token",required=true,dataType="String",paramType="header")})
	@ApiOperation(value="List All Courts",notes="Api to List All Court")
	@RequestMapping(value="listAll",method=RequestMethod.GET)
	public ResponseEntity<Object> listAllCourt(){
		LOG.debug("List All Courts");
		List<CourtDto> court=courtService.listAllCourts();
		
		return new ResponseEntity<Object>(court,HttpStatus.OK);
	}
	
	
	@ApiOperation(value="Get Court By Address")
	@RequestMapping(value="/address",method=RequestMethod.GET)
	public ResponseEntity<Object> getCourtByAddress(@RequestHeader Long customerId){
		LOG.debug("Request Accepted to get court By Address..");
		List<CourtByAddressDto> addressresponse=courtService.getCourtAddress(customerId);
		return new ResponseEntity<Object>(addressresponse,HttpStatus.OK);
		
	}

}
