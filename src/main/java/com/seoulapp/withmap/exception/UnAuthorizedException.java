package com.seoulapp.withmap.exception;

import com.seoulapp.withmap.model.error.ErrorType;

public class UnAuthorizedException extends ErrorEntityException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnAuthorizedException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}