package com.ozads.futsalnepal.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozads.futsalnepal.dto.TimeSlotByCourtDto;
import com.ozads.futsalnepal.request.BookingCreatationRequest;
import com.ozads.futsalnepal.request.TimeSlotRequest;
import com.ozads.futsalnepal.response.TimeSlotResponse;
import com.ozads.futsalnepal.services.TimeSlotService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="api/v1/timeSlot")
public class TimeSlotController {

	private static final Logger LOG = LoggerFactory.getLogger(TimeSlotController.class);
		@Autowired
		TimeSlotService timeSlotService;
		
		//@ApiImplicitParams({
				//@ApiImplicitParam(name="token",required=true,dataType="String",paramType="header")})
			@ApiOperation(value="Save TimeSlot")
			@RequestMapping( method=RequestMethod.POST)
			public ResponseEntity<Object> saveBooking(@RequestHeader Long courtId,
					@RequestBody TimeSlotRequest timeSlotRequest ){
				LOG.debug("Request Accepted ");
				timeSlotService.saveTimeSlot(courtId,timeSlotRequest);
				return new ResponseEntity<Object>("TimeSlot added",HttpStatus.CREATED);
			}
			
		
		@RequestMapping(value="/listAll",method=RequestMethod.GET)
		@ApiOperation(value="List TimeSlots ",notes="Api to List all timeSlots")
		public ResponseEntity<Object> listAllTimeSlots(){
		LOG.debug("request Accepted to List All Address");
			List<TimeSlotResponse> timeSlotResponse=timeSlotService.listAllTimeSlots();

		return new ResponseEntity<Object>(timeSlotResponse,HttpStatus.OK);
		}
		
		@RequestMapping(value="/{courtId}",method=RequestMethod.GET)
		@ApiOperation(value="List TimeSlots ",notes="Api to List all timeSlots")
		public ResponseEntity<Object>  getTimeSlotByCourtId(@PathVariable ("courtId") Long courtId){
		LOG.debug("request Accepted to List All Address");
			List<TimeSlotByCourtDto> timeSlotResponse=timeSlotService. getTimeSlotById(courtId);

		return new ResponseEntity<Object>(timeSlotResponse,HttpStatus.OK);
		}
		
		
		//@ApiImplicitParams({
				//@ApiImplicitParam(name="token",required=true,dataType="String",paramType="header")})
			@ApiOperation(value="Delete TimeSlot",notes="Api to Delete Court")
			@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
			public ResponseEntity<Object> deleteTimeSlot(@PathVariable ("id") Long id){
				  LOG.debug("Court Delete Request");
				  timeSlotService.deleteTimeSlot(id);
				return new ResponseEntity<Object>("TimeSlot Deleted",HttpStatus.OK);
			}
			
}
