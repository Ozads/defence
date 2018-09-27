package com.ozads.futsalnepal.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozads.futsalnepal.exceptions.NotFoundException;
import com.ozads.futsalnepal.model.Booking;
import com.ozads.futsalnepal.model.Court;
import com.ozads.futsalnepal.model.TimeSlot;
import com.ozads.futsalnepal.model.Customer;
import com.ozads.futsalnepal.repository.BookingRepository;
import com.ozads.futsalnepal.repository.CourtRepository;
import com.ozads.futsalnepal.repository.CustomerRepository;
import com.ozads.futsalnepal.repository.TimeSlotRepository;
import com.ozads.futsalnepal.request.BookingCreatationRequest;
import com.ozads.futsalnepal.response.CourtBookingResponse;
import com.ozads.futsalnepal.response.CustomerBookingResponse;
import com.ozads.futsalnepal.util.BookingStatus;
import com.ozads.futsalnepal.util.Status;

@Service
public class BookingService {

	private static final Logger LOG = LoggerFactory.getLogger(BookingService.class);

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	CourtRepository courtRepository;
	
	@Autowired
	TimeSlotRepository timeSlotRepository;

	
	public Booking saveBooking(Long customerId,BookingCreatationRequest bookingRequest) {
		LOG.debug("Request Accepted to Save booking ");
	
		Customer customer = customerRepository.findCustomerByIdAndStatusNot(customerId, Status.DELETED);
		if (customer == null) {
			throw new NotFoundException("Customer Not Found");
		}
		
		Court court =courtRepository.findCourtById(bookingRequest.getCourtId());
		if(court==null) {
			throw new NotFoundException("Court  Not Found");
			
		}
		System.out.println(customerId);
		
		System.out.println(bookingRequest.getCourtId());
		
		Booking booking = new Booking();
		booking.setBookingDate(new Date());
		TimeSlot ts = timeSlotRepository.findTimeSlotById(bookingRequest.getSlotId());
		booking.setTimeSlot(ts.getTimeSlot());
		booking.setBookingStatus(BookingStatus.AVAILABLE);
		booking.setCustomer(customer);
		booking.setCourt(court);
		Booking bs=bookingRepository.save(booking);
		
		LOG.debug("The booking has been set");

		return bs;
	}

	
//	public List<BookingResponseDto> listAllBooking() {
//		LOG.debug("Request Accepted to List All booking");
//		List<Booking> booking= bookingRepository.findBookingByBookingStatusNot(BookingStatus.AVAILABLE);
//		List<BookingResponseDto> bookingResponse=new ArrayList<>();
//		booking.stream().forEach(u->{
//			Customer c=customerRepository.findCustomerById(u.getCustomer().getId());
//			if(c==null) {
//				throw new NotFoundException("Customer Not found");
//			}
//			Court s=courtRepository.findCourtById(u.getCourt().getId());
//			if(s==null) {
//				throw new NotFoundException("Court Not found");
//			}
//			BookingResponseDto responseDto=new BookingResponseDto();
//			responseDto.setId(u.getId());
//			responseDto.setTimeSlot(u.getTimeSlot());
//			responseDto.setBookingName(u.getBookingName());
//			responseDto.setBookingDate(u.getBookingDate());
//			responseDto.setPrice(u.getPrice());
//			responseDto.setBookingBy(c.getFullName());
//			responseDto.getBookingBy();
//			
//			List<AddressResponseDto> adddresss=new ArrayList<>();
//			List<Address> add=c.getAddress();
//			if (add != null) {
//				add.stream().forEach(a -> {
//					AddressResponseDto dd = new AddressResponseDto();
//					dd.setId(a.getId());
//					dd.setDistrict(a.getDistrict());
//					dd.setLocality(a.getLocality());
//					dd.setWardNo(a.getWardNo());
//					
//					adddresss.add(dd);
//				});
//			}
//			
//			responseDto.setAddress(adddresss);
//			
//			List<CourtAddressResponse> adddres=new ArrayList<>();
//			List<CourtAddress> adds=s.getCourtAddress();
//			if (adds != null) {
//				adds.stream().forEach(a -> {
//					CourtAddressResponse dd = new CourtAddressResponse();
//					dd.setId(a.getId());
//					dd.setDistrict(a.getDistrict());
//					dd.setLocality(a.getLocality());
//					dd.setWardNo(a.getWardNo());
//					
//					adddres.add(dd);
//				});
//			}
//			responseDto.setCourt(adddres);
//			bookingResponse.add(responseDto);
//	
//			
//		});
//		LOG.debug("The booking is obtained");
//		return bookingResponse;
//		
//	}

	
	public List<CourtBookingResponse> listAllbookingByCourt(Long courtId) {
		LOG.debug("List All TimeSlot in Court");
		List<CourtBookingResponse> bookingResponses=new ArrayList<>();
		Court court=courtRepository.findCourtById(courtId);
	
		if(court==null) {
			throw new NotFoundException("Court not found");
		}
		
		
		List<Booking> booking=bookingRepository.findBookingByCourtAndBookingStatus(court,BookingStatus.AVAILABLE);
		
		if(booking==null) {
			throw new NotFoundException("Booking Not Avaliable in your court");
		}
		
		
		booking.stream().forEach(u->{
			CourtBookingResponse bookingResponseDto=new CourtBookingResponse();
			
			bookingResponseDto.setId(u.getId());
			
			bookingResponseDto.setTimeSlot(u.getTimeSlot());
			bookingResponseDto.setBookingDate(u.getBookingDate());
			bookingResponseDto.setCustomerEmail(u.getCustomer().getEmail());
			bookingResponseDto.setCustomerPhone(u.getCustomer().getPhoneNo());
			bookingResponseDto.setBookingBy(u.getCustomer().getFullName());
			
			
			
			bookingResponses.add(bookingResponseDto);
		});
		LOG.debug("List of all Avaliable booking");
		return bookingResponses;
	}
	
	
	public List<CustomerBookingResponse> listAllbookingByCustomer(Long customerId) {
		LOG.debug("List All TimeSlot in Court");
		List<CustomerBookingResponse> bookingResponses=new ArrayList<>();
		Customer customer=customerRepository.findCustomerById(customerId);
	
		if(customer==null) {
			throw new NotFoundException("Customer not found");
		}
		
		
		List<Booking> booking=bookingRepository.findBookingByCustomerAndBookingStatus(customer,BookingStatus.AVAILABLE);
		
		if(booking==null) {
			throw new NotFoundException("Booking Not Avaliable in your court");
		}
		
		
		booking.stream().forEach(u->{
			CustomerBookingResponse bookingResponseDto=new CustomerBookingResponse();
			
			bookingResponseDto.setId(u.getId());
				
			bookingResponseDto.setTimeSlot(u.getTimeSlot());
			bookingResponseDto.setBookingDate(u.getBookingDate());
			bookingResponseDto.setCourtEmail(u.getCourt().getEmail());
			bookingResponseDto.setCourtPhone(u.getCourt().getPhoneNo());
			
			bookingResponseDto.setBookingTo(u.getCourt().getCourtName());
			
			
			
			bookingResponses.add(bookingResponseDto);
		});
		LOG.debug("List of all Avaliable booking");
		return bookingResponses;
	}
	
	
	
	@Transactional
	public void deleteBooking(Long bookingId) {
		LOG.debug("Request Accepted to Delete booking");
		Booking booking=bookingRepository.findBookingByIdAndBookingStatusNot(bookingId,BookingStatus.DELETED);
		if(booking==null) {
			throw new NotFoundException("Booking not found");
		}
		
		booking.setBookingStatus(BookingStatus.DELETED);
		bookingRepository.delete(booking);
		LOG.debug("Booking Deleted");
	}
}
