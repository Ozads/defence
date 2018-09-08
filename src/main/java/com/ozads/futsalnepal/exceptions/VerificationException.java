package com.ozads.futsalnepal.exceptions;

import org.hibernate.service.spi.ServiceException;


@SuppressWarnings("serial")
public class VerificationException extends ServiceException {

	public VerificationException(String message) {
		super(message);
		
	}

}
