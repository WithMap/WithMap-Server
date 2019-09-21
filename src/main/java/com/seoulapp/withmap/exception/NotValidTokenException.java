package com.seoulapp.withmap.exception;

import com.seoulapp.withmap.model.error.ErrorType;

public class NotValidTokenException extends ErrorEntityException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotValidTokenException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}