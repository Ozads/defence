package com.ozads.futsalnepal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ozads.futsalnepal.dto.CustomerDto;
import com.ozads.futsalnepal.request.CustomerCreationRequest;

import com.ozads.futsalnepal.response.CustomerResponseDto;
import com.ozads.futsalnepal.services.CustomerService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value="api/v1/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);
	@ApiOperation(value="Post customer",notes="Api to post customer")
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> createCustomer(@Validated @RequestBody CustomerCreationRequest 
			customerCreationRequest){
		   LOG.debug("Customer Creation request");
		   customerService.saveCustomer(customerCreationRequest);
		return new ResponseEntity<Object>("Registered Sucessfull !Check Email to verify your account.",HttpStatus.CREATED);
	}
	
	
	//@ApiImplicitParams({
		//@ApiImplicitParam(name="token",required=true,dataType="String",paramType="header")})
	@ApiOperation(value="Delete customer",notes="Api to delete customer")
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteCustomer(@PathVariable ("id") Long id){
		  LOG.debug("Customer Delete Request");
		customerService.deleteCustomer(id);
		return new ResponseEntity<Object>("Customer Deleted",HttpStatus.OK);
	}
	

	@ApiOperation(value="List Customers",notes="Api to List Customer")
	//@ApiImplicitParams({
		//@ApiImplicitParam(name="token",required=true,dataType="String",paramType="header")})
	@RequestMapping(value="/listAll",method=RequestMethod.GET)
	public ResponseEntity<Object> listAllCustomer(@RequestHeader Long loginId){
		List<CustomerDto> customer=customerService.listAllCustomer();
		
		return new ResponseEntity<Object>(customer,HttpStatus.OK);
	}
	
	
	@ApiOperation(value="Verification",notes="Api to get verification")
	@RequestMapping(value="/{value}",method=RequestMethod.GET)
	public ResponseEntity<Object> getVerification(@PathVariable ("value") String token){
		LOG.debug("Request To verify Account");
		customerService.getVerify(token);
		return new ResponseEntity<Object>("Sucessfully Verified Login to continue",HttpStatus.OK);
	}
	
	

}
