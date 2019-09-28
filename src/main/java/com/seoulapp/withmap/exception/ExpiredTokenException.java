package com.seoulapp.withmap.exception;

import com.seoulapp.withmap.model.error.ErrorType;

public class ExpiredTokenException extends ErrorEntityException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExpiredTokenException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}

