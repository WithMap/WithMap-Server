package com.seoulapp.withmap.exception;

import com.seoulapp.withmap.model.error.ErrorType;

public class UnAuthenticationException extends ErrorEntityException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnAuthenticationException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}