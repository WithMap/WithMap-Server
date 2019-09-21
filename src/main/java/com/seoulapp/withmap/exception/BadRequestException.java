package com.seoulapp.withmap.exception;

import com.seoulapp.withmap.model.error.ErrorType;

public class BadRequestException extends ErrorEntityException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadRequestException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}
