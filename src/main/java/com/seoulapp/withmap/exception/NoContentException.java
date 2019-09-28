package com.seoulapp.withmap.exception;

import com.seoulapp.withmap.model.error.ErrorType;

public class NoContentException extends ErrorEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoContentException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}
