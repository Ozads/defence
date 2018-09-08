package com.ozads.futsalnepal.exceptions;

import org.hibernate.service.spi.ServiceException;


@SuppressWarnings("serial")
public class RequiredException extends ServiceException  {

	
	public RequiredException(String message) {
		super(message);
		
	}

}
