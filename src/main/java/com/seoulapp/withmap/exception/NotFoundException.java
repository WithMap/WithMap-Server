package com.seoulapp.withmap.exception;

import com.seoulapp.withmap.model.error.ErrorType;

public class NotFoundException extends ErrorEntityException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotFoundException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}