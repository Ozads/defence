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

import com.ozads.futsalnepal.dto.CustomerDto;
import com.ozads.futsalnepal.exceptions.AlreadyExistException;
import com.ozads.futsalnepal.exceptions.ExpireException;
import com.ozads.futsalnepal.exceptions.NotFoundException;


import com.ozads.futsalnepal.model.Customer;
import com.ozads.futsalnepal.model.Login;
import com.ozads.futsalnepal.model.Verification;

import com.ozads.futsalnepal.repository.CustomerRepository;
import com.ozads.futsalnepal.repository.LoginRepository;
import com.ozads.futsalnepal.repository.VerificationRepository;


import com.ozads.futsalnepal.request.CustomerCreationRequest;


import com.ozads.futsalnepal.response.CustomerResponseDto;
import com.ozads.futsalnepal.util.DateUtil;
import com.ozads.futsalnepal.util.EmailUtility;
import com.ozads.futsalnepal.util.LoginStatus;
import com.ozads.futsalnepal.util.LoginType;
import com.ozads.futsalnepal.util.Md5Hashing;
import com.ozads.futsalnepal.util.Status;
import com.ozads.futsalnepal.util.TokenGenerator;
import com.ozads.futsalnepal.util.VerificationStatus;



@Service
public class CustomerService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	LoginRepository loginRepository;

	@Autowired
	LoginService loginService;

	

	@Autowired
	VerificationRepository verificationRepository;

	@Autowired
	VerificationService verificationService;

	@Transactional
	public Customer saveCustomer(CustomerCreationRequest customerCreationRequest) {
		LOG.debug("Customer Creation started..");
		Login l = loginRepository.findLoginByUsernameAndStatusNot(customerCreationRequest.getUsername(), Status.DELETED);
		if (l != null) {
			throw new AlreadyExistException("Username Already Exits");
		}

		Customer c = customerRepository.findByEmailAndStatusNot(customerCreationRequest.getEmail(), Status.DELETED);
		if (c != null) {
			throw new AlreadyExistException("Email already Exits !!");
		}
		
		Customer cc = customerRepository.findByPhoneNoAndStatusNot(customerCreationRequest.getPhoneNo(), Status.DELETED);
		if (cc != null) {
			throw new AlreadyExistException("Phone Number already Exits !!");
		}
		
		System.out.println(customerCreationRequest.getEmail().toString());
		Customer customer = new Customer();
		customer.setFullName(customerCreationRequest.getFullName());
		
		customer.setEmail(customerCreationRequest.getEmail());
		
		customer.setPhoneNo(customerCreationRequest.getPhoneNo());
		customer.setStatus(Status.ACTIVE);
		customer.setUsername(customerCreationRequest.getUsername());
		customer.setAddress(customerCreationRequest.getAddress());
		
		customer.setCreatedDate(new Date());
		
		Customer savedCustomer = customerRepository.save(customer);
		
		TokenGenerator tg = new TokenGenerator();
		String token = tg.generateToken(customerCreationRequest.getUsername());

		Verification verification = verificationRepository
				.findVerificationByEmailAndStatusNot(customerCreationRequest.getEmail(), VerificationStatus.EXPIRE);

		if(verification!=null) {
			throw new AlreadyExistException("Email Already Exits please verify");
		}
		
		if (verification == null) {
			Verification verifiy = new Verification();
			verifiy.setEmail(customerCreationRequest.getEmail());
			verifiy.setCreatedDate(new Date());
			verifiy.setExpireDate(DateUtil.getTokenExpireDate(new Date()));
			verifiy.setToken(token);
			verifiy.setStatus(VerificationStatus.ACTIVE);
			EmailUtility.sendVerification(customerCreationRequest.getEmail(), token);
			verificationService.saveVerification(verifiy);
			
		}
		
	

		LOG.debug("Added.");
		LOG.debug("Customer Adding..");
		
		LOG.debug("Customer Added");
		if (savedCustomer != null) {
			Login login = new Login();
			login.setLoginStatus(LoginStatus.LOGOUT);
			try {
				login.setPassword(Md5Hashing.getPw(customerCreationRequest.getPassword()));
				login.setCreatedDate(new Date());
				login.setEmail(customerCreationRequest.getEmail());
				login.setUsername(customerCreationRequest.getUsername());
				login.setCustomer(savedCustomer);
				login.setLoginType(LoginType.CUSTOMER);
				login.setStatus(Status.BLOCKED);
				loginService.saveLogin(login);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

		
//			List<CustomerAddressCreationRequest> address = customerCreationRequest.getAddress();
//			if (address != null) {
//				for (CustomerAddressCreationRequest add : address) {
//					Address addresses = new Address();
//					addresses.setPlace(add.getPlace());
//					addresses.setCustomer(savedCustomer);
//
//					addressRepository.save(addresses);
//					LOG.debug("Address Added");
//				}
//			}

		}

		return customer;
	}
	
	public void deleteCustomer(Long id) {
		LOG.debug("Deleting Customer..");
		Customer c = customerRepository.findCustomerById(id);
		if (c == null) {
			throw new NotFoundException("Customer Not found !!");
		}

		Login l = loginRepository.findLoginByEmailAndStatusNot(c.getEmail(), Status.DELETED);
		if (l == null) {
			throw new NotFoundException("Customer Not found !!");
		}
		l.setStatus(Status.DELETED);
		c.setStatus(Status.DELETED);
		LOG.debug("Customer Deleted");
		customerRepository.save(c);
		loginRepository.save(l);
	}
	
	
	
	public List<CustomerDto> listAllCustomer() {
		LOG.debug("Request to get All customer");
		List<Customer> customer = customerRepository.findAllCustomerByStatusNot(Status.DELETED);
		List<CustomerDto> customers = new ArrayList<>();
		if (customer == null) {
			throw new NotFoundException("Customer not found");
		}
		customer.stream().forEach(u -> {
			CustomerDto customerDto = new CustomerDto();
			customerDto.setId(u.getId());
			customerDto.setFullName(u.getFullName());
			customerDto.setEmail(u.getEmail());
			
			customerDto.setUsername(u.getUsername());
			customerDto.setPhoneNo(u.getPhoneNo());
			customerDto.setAddress(u.getAddress());
//			List<AddressDto> adddresss = new ArrayList<>();
//			List<Address> add = u.getAddress();
//			if (add != null) {
//				add.stream().forEach(a -> {
//					AddressDto dd = new AddressDto();
//					dd.setId(a.getId());
//					dd.setPlace(a.getPlace());
//					adddresss.add(dd);
//				});
//			}
//			customerDto.setAddress(adddresss);
			customers.add(customerDto);

		});
		LOG.debug("All customer Obtain");
		return customers;
	}
	
	public void getVerify(String token) {

		Verification v = verificationRepository.findVerificationByTokenAndStatusNot(token, VerificationStatus.EXPIRE);
		if (v == null) {
			throw new ExpireException("The session in invallied");
		}

		if (DateUtil.compareDate(v.getCreatedDate(), v.getExpireDate()) == false) {
			v.setStatus(VerificationStatus.EXPIRE);
			verificationService.saveVerification(v);
			throw new ExpireException("Sorry !! Token is expired");
		}

		Login l = loginRepository.findLoginByEmailAndStatus(v.getEmail(), Status.BLOCKED);
		if (l != null) {
			l.setStatus(Status.ACTIVE);
			v.setStatus(VerificationStatus.EXPIRE);
			verificationService.saveVerification(v);
			loginRepository.save(l);
		}
	}
	

}
