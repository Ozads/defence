package com.ozads.futsalnepal.services;


import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozads.futsalnepal.dto.CourtDto;
import com.ozads.futsalnepal.exceptions.AlreadyExistException;
import com.ozads.futsalnepal.exceptions.NotFoundException;


import com.ozads.futsalnepal.model.Customer;
import com.ozads.futsalnepal.model.Login;
import com.ozads.futsalnepal.model.Court;

import com.ozads.futsalnepal.model.User;
import com.ozads.futsalnepal.model.Verification;
import com.ozads.futsalnepal.repository.CustomerRepository;
import com.ozads.futsalnepal.repository.LoginRepository;

import com.ozads.futsalnepal.repository.CourtRepository;
import com.ozads.futsalnepal.repository.UserRepository;
import com.ozads.futsalnepal.repository.VerificationRepository;

import com.ozads.futsalnepal.request.CourtCreatationRequest;


import com.ozads.futsalnepal.response.CourtByAddressDto;
import com.ozads.futsalnepal.util.DateUtil;
import com.ozads.futsalnepal.util.EmailUtility;
import com.ozads.futsalnepal.util.LoginStatus;
import com.ozads.futsalnepal.util.LoginType;
import com.ozads.futsalnepal.util.Md5Hashing;
import com.ozads.futsalnepal.util.RandomPassword;
import com.ozads.futsalnepal.util.Status;
import com.ozads.futsalnepal.util.TokenGenerator;
import com.ozads.futsalnepal.util.VerificationStatus;


@Service
public class CourtService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CourtService.class);

	@Autowired
	LoginRepository loginRepository;

	@Autowired
	LoginService loginService;

	@Autowired
	CourtRepository courtRepository;

	

	@Autowired
	UserRepository userRepository;

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	VerificationRepository verificationRepository;
	
	@Autowired
	VerificationService verificationService;

	
	@Transactional
	public Court saveCourt(CourtCreatationRequest courtCreationRequest) {
		LOG.debug("Message For Court Creatation");
		
		

		Login l = loginRepository.findLoginByUsername(courtCreationRequest.getUsername());
		if (l != null) {
			throw new AlreadyExistException("Username Already Exits");
		}

		Court s = courtRepository.findByPhoneNoAndStatusNot(courtCreationRequest.getPhoneNo(), Status.DELETED);
		if (s != null) {
			throw new AlreadyExistException("Phone no Already Exist");
		}

		Court court = new Court();
		court.setCourtName(courtCreationRequest.getCourtName());
		
		court.setPhoneNo(courtCreationRequest.getPhoneNo());
		court.setStatus(Status.ACTIVE);
		court.setCreatedDate(new Date());

		court.setPrice(courtCreationRequest.getPrice());
		court.setUsername(courtCreationRequest.getUsername());
		court.setEmail(courtCreationRequest.getEmail());
		court.setCourtAddress(courtCreationRequest.getCourtAddress());
		LOG.debug("Adding Court....");
		Court ss = courtRepository.save(court);
		LOG.debug("Court Added");
		if (ss != null) {
//			List<CourtAddressCreatationRequest> address = courtCreationRequest.getCourtAddress();
//			if (address != null) {
//				LOG.debug("Address Adding");
//				for (CourtAddressCreatationRequest add : address) {
//					CourtAddress addresses = new CourtAddress();
//					addresses.setPlace(add.getPlace());
//					
//					addresses.setCourt(ss);
//
//					courtAddressRepository.save(addresses);
//					LOG.debug("Address Added");
//				}
//			}

			Login login = new Login();
			try {
				String password= RandomPassword.newPassword();
				login.setPassword(Md5Hashing.getPw(password));
				login.setEmail(courtCreationRequest.getEmail());
				login.setLoginStatus(LoginStatus.LOGOUT);
				login.setUsername(courtCreationRequest.getUsername());
				login.setCourt(ss);
				login.setCreatedDate(new Date());
				login.setStatus(Status.ACTIVE);
				login.setLoginType(LoginType.COURT);
				Login ll = loginRepository.save(login);
				if (ll != null) {
					EmailUtility.sendNewPassword(courtCreationRequest.getEmail(), password,courtCreationRequest.getUsername());
				}

				LOG.debug("Login Added");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return court;
		
//		TokenGenerator tg = new TokenGenerator();
//		String token = tg.generateToken(courtCreationRequest.getEmail());
//
//		Verification verification = verificationRepository
//				.findVerificationByEmailAndStatusNot(courtCreationRequest.getEmail(), VerificationStatus.EXPIRE);
//
//		if (verification == null) {
//			Verification verifiy = new Verification();
//			verifiy.setEmail(courtCreationRequest.getEmail());
//			verifiy.setCreatedDate(new Date());
//			verifiy.setExpireDate(DateUtil.getTokenExpireDate(new Date()));
//			verifiy.setToken(token);
//			verifiy.setStatus(VerificationStatus.ACTIVE);
//			EmailUtility.sendVerification(courtCreationRequest.getEmail(), token);
//			verificationService.saveVerification(verifiy);
//		}
//		
//	
//
//		LOG.debug("Added.");
//		LOG.debug("Court Adding..");
//		Court savedCourt = courtRepository.save(court);
//		LOG.debug("Customer Added");
//		if (savedCourt != null) {
//			Login login = new Login();
//			login.setLoginStatus(LoginStatus.LOGOUT);
//			try {
//				login.setPassword(Md5Hashing.getPw(courtCreationRequest.getPassword()));
//				login.setCreatedDate(new Date());
//				login.setEmail(courtCreationRequest.getEmail());
//				login.setUsername(courtCreationRequest.getUsername());
//				login.setCourt(savedCourt);
//				login.setLoginType(LoginType.CUSTOMER);
//				login.setStatus(Status.BLOCKED);
//				loginService.saveLogin(login);
//			} catch (NoSuchAlgorithmException e) {
//				e.printStackTrace();
//			}
//
//			
//			List<CourtAddressCreatationRequest> address = courtCreationRequest.getCourtAddress();
//			if (address != null) {
//				for (CourtAddressCreatationRequest add : address) {
//					CourtAddress addresses = new CourtAddress();
//					addresses.setPlace(add.getPlace());
//					addresses.setCourt(savedCourt);
//
//					courtAddressRepository.save(addresses);
//					LOG.debug("Address Added");
//				}
//			}
//
//		}
//
//		return court;
	}
	
	@Transactional
	public void deleteCourt(Long userId, Long id) {
		LOG.debug("Deleteing ..");

		Court court = courtRepository.findCourtByIdAndStatusNot(id, Status.DELETED);

		if (court == null) {
			throw new NotFoundException("Court Not found");

		}

		User user = userRepository.findUserByIdAndStatusNot(userId, Status.DELETED);

		if (user == null) {
			throw new NotFoundException("User Not found");

		}

		Login l = loginRepository.findLoginByEmailAndStatusNot(court.getEmail(), Status.DELETED);
		if (l == null) {
			throw new NotFoundException("Court Not found !!");
		}
		l.setStatus(Status.DELETED);
		court.setStatus(Status.DELETED);
		LOG.debug("Court Deleted..");
		courtRepository.save(court);

	}	
	
	private void emailDuplication(String email, Court court) {
		LOG.debug("Check for Email duplication");

		Court s = courtRepository.findByEmailAndStatusNot(email, Status.DELETED);
		if (s != null && court.getId().equals(s.getId())) {

			throw new AlreadyExistException("Email Already Exit");

		}
	}
	
	public List<CourtDto> listAllCourts() {
		LOG.debug("Request Accepted to list all courts");
		List<Court> court = courtRepository.findAllCourtByStatusNot(Status.DELETED);
		List<CourtDto> courtDto = new ArrayList<>();

		court.stream().forEach(u -> {
			CourtDto dto = new CourtDto();
			dto.setId(u.getId());
			dto.setCourtName(u.getCourtName());
			dto.setEmail(u.getEmail());
			dto.setPhoneNo(u.getPhoneNo());
			dto.setUsername(u.getUsername());
			dto.setPrice(u.getPrice());	
			dto.setAddress(u.getCourtAddress());
//			List<CourtAddressDto> courtAddress = new ArrayList<>();
//			List<CourtAddress> addresses = u.getCourtAddress();
//			if (addresses != null) {
//				addresses.stream().forEach(a -> {
//					CourtAddressDto courtAddressDto = new CourtAddressDto();
//					courtAddressDto.setId(a.getId());
//					courtAddressDto.setPlace(a.getPlace());
//					
//					courtAddress.add(courtAddressDto);
//				});
//			}
//			dto.setAddress(courtAddress);
//			
			courtDto.add(dto);
		});
			
		LOG.debug("All Court List is obtain");

		return courtDto;
	}
	
	public List<CourtByAddressDto> getCourtAddress(Long customerId) {

		Customer customer=customerRepository.findByIdAndStatusNot(customerId, Status.DELETED);
		if(customer==null) {
			throw new NotFoundException("Customer Not found !!");
		}
		List<CourtByAddressDto> courtDto=new ArrayList<>();
		String add = customer.getAddress();
		if (add != null) {
			List<Court> court=courtRepository.findByCourtAddressAndStatusNot(add,Status.DELETED);
//			stream().forEach(u -> {
//				String place=u.getPlace();
//				
//				System.out.println(place);
//				
//				List<CourtAddress> address=courtAddressRepository.
//						findAddressByPlace(place);
//
							court.stream().forEach(a->{
							CourtByAddressDto dto=new CourtByAddressDto();
							dto.setCourtName(a.getCourtName());
							dto.setPhoneNo(a.getPhoneNo());
							dto.setEmail(a.getEmail());
							dto.setPrice(a.getPrice());
						
							courtDto.add(dto);
						});
			
		}
		
		
		
		return courtDto;

	}

}
