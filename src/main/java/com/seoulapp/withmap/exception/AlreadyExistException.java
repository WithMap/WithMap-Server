package com.seoulapp.withmap.exception;

import com.seoulapp.withmap.model.error.ErrorType;

public class AlreadyExistException  extends ErrorEntityException {

	private static final long serialVersionUID = 1L;

	public AlreadyExistException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}
