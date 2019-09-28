package com.seoulapp.withmap.exception;

import com.seoulapp.withmap.model.error.ErrorType;

public class ForbiddenException extends ErrorEntityException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ForbiddenException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}
