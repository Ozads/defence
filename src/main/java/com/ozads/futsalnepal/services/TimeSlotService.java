package com.ozads.futsalnepal.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ozads.futsalnepal.dto.TimeSlotByCourtDto;
import com.ozads.futsalnepal.exceptions.NotFoundException;
import com.ozads.futsalnepal.model.Booking;
import com.ozads.futsalnepal.model.Court;
import com.ozads.futsalnepal.model.Customer;
import com.ozads.futsalnepal.model.TimeSlot;
import com.ozads.futsalnepal.repository.CourtRepository;
import com.ozads.futsalnepal.repository.TimeSlotRepository;
import com.ozads.futsalnepal.request.BookingCreatationRequest;
import com.ozads.futsalnepal.request.TimeSlotRequest;
import com.ozads.futsalnepal.response.CourtBookingResponse;
import com.ozads.futsalnepal.response.CourtByAddressDto;
import com.ozads.futsalnepal.response.TimeSlotResponse;
import com.ozads.futsalnepal.util.BookingStatus;
import com.ozads.futsalnepal.util.Status;

@Service
public class TimeSlotService {
	
//	private static final Logger LOG = LoggerFactory.getLogger(TimeSlotService.class);
//	
//	@Autowired
//	CourtRepository courtRepository;
//	
//
//	@Autowired
//	TimeSlotRepository timeSlotRepository;
//	
//	public TimeSlot saveTimeSlot(Long courtId,TimeSlotRequest timeSlotRequest) {
//		LOG.debug("Request Accepted to Save booking ");
//	
//		Court court = courtRepository.findCourtByIdAndStatusNot(courtId, Status.DELETED);
//		if (court == null) {
//			throw new NotFoundException("Court Not Found");
//		}
//		
//		TimeSlot timeSlot= new TimeSlot();
//		
//		
//		
//		timeSlot.setTimeSlot(timeSlotRequest.getTimeSlot());
//		
//		timeSlot.setCourt(new Court(courtId));
//		timeSlotRepository.save(timeSlot);
//		
//		LOG.debug("The booking has been set");
//
//		return timeSlot;
//	}
	
	private static final Logger LOG = LoggerFactory.getLogger(TimeSlotService.class);

	@Autowired
	TimeSlotRepository timeSlotRepository;
	
	@Autowired
	CourtRepository courtRepository;

	
	public TimeSlot saveTimeSlot(Long courtId,TimeSlotRequest timeSlotRequest) {
		LOG.debug("Request Accepted to Save timeslot ");
	
		
		
		Court court =courtRepository.findCourtByIdAndStatusNot(courtId, Status.DELETED);
		if(court==null) {
			throw new NotFoundException("Court  Not Found");
			
		}
		System.out.println(courtId);
		
		
		
		TimeSlot timeSlot=new TimeSlot();
		
		
		timeSlot.setTimeSlot(timeSlotRequest.getTimeSlot());
		
		
		
		
		timeSlot.setCourtId(court.getId());
		timeSlot.setCourtName(court.getCourtName());
		TimeSlot ts=timeSlotRepository.save(timeSlot);
		
		LOG.debug("The booking has been set");

		return ts;
	}
	
	
	public List<TimeSlotByCourtDto> getTimeSlotById(Long courtId) {

		Court court=courtRepository.findByIdAndStatusNot(courtId, Status.DELETED);
		if(court==null) {
			throw new NotFoundException("Court Not found !!");
		}
		List<TimeSlotByCourtDto> timeSlotDto=new ArrayList<>();
		Long id = court.getId();
		if (courtId != null) {
			List<TimeSlot> timeSlot=timeSlotRepository.findTimeSlotByCourtId(courtId);

							timeSlot.stream().forEach(a->{
							TimeSlotByCourtDto dto=new TimeSlotByCourtDto();
							dto.setId(a.getId());
							dto.setTimeSlot(a.getTimeSlot());
						
							timeSlotDto.add(dto);
						});
			
		}
		
		
		
		return timeSlotDto;

	}
	
	@Transactional
	public void deleteTimeSlot(Long timeSlotId) {
		LOG.debug("Request Accepted to Delete booking");
		TimeSlot timeSlot=timeSlotRepository.findTimeSlotById(timeSlotId);
		if(timeSlot==null) {
			throw new NotFoundException("TimeSlot not found");
		}
		
		
		timeSlotRepository.delete(timeSlot);
		LOG.debug("Booking Deleted");
	}
	

	
	public List<TimeSlotResponse> listAllTimeSlots(){
		
		List<TimeSlot> timeSlot=timeSlotRepository.findAll();
		List<TimeSlotResponse> timeSlotResponses=new ArrayList<>();
		timeSlot.stream().forEach(u->{
			TimeSlotResponse response =new TimeSlotResponse();
			response.setId(u.getId());
			response.setTimeSlot(u.getTimeSlot());
			response.setCourtName(u.getCourtName());
			timeSlotResponses.add(response);
		});
		return timeSlotResponses;
	}
}
//	
//	public List<TimeSlotResponse> listAllTimeSlotByCourt(Long courtId) {
//		LOG.debug("List All TimeSlot in Court");
//		List<TimeSlotResponse> TimeSlotResponses=new ArrayList<>();
//		Court court=courtRepository.findCourtById(courtId);
//	
//		if(court==null) {
//			throw new NotFoundException("Court not found");
//		}
//		
//		
//		List<TimeSlot> timeSlot=TimeSlotRepository.findTimeSlotByCourt(court);
//		
//		if(timeSlot==null) {
//			throw new NotFoundException("TimeSlot Not Avaliable in your court");
//		}
//		
//		
//		timeSLot.stream().forEach(u->{
//			CourtBookingResponse bookingResponseDto=new CourtBookingResponse();
//			System.out.println(u.getBookingName());
//			bookingResponseDto.setId(u.getId());
//			bookingResponseDto.setBookingName(u.getBookingName());
//			bookingResponseDto.setTimeSlot(u.getTimeSlot());
//			bookingResponseDto.setBookingDate(u.getBookingDate());
//			
//			
//			bookingResponseDto.setBookingBy(u.getCustomer().getFullName());
//			
//			
//			
//			bookingResponses.add(bookingResponseDto);
//		});
//		LOG.debug("List of all Avaliable booking");
//		return bookingResponses;
//	}
//}
