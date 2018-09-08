package com.ozads.futsalnepal.exceptions;

import org.hibernate.service.spi.ServiceException;


@SuppressWarnings("serial")
public class LogoutFailException extends ServiceException {

	
	public LogoutFailException(String message) {
		super(message);
		
	}

}
