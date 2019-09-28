package com.seoulapp.withmap.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.seoulapp.withmap.model.error.ErrorEntity;
import com.seoulapp.withmap.model.error.ErrorType;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionAdvisor {

	@ExceptionHandler({ UnAuthenticationException.class, UnAuthorizedException.class, ExpiredTokenException.class,
			NotValidTokenException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorEntity handleUnAuthenticationException(ErrorEntityException exception) {
		return exception.entity();
	}

	@ExceptionHandler(ForbiddenException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorEntity handleUnAuthorizedException(ErrorEntityException exception) {
		return exception.entity();
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorEntity handleNotFoundException(ErrorEntityException exception) {
		return exception.entity();
	}

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorEntity handleBadRequestException(ErrorEntityException exception) {
		return exception.entity();
	}

	@ExceptionHandler(NoContentException.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ErrorEntity handleNoContentException(ErrorEntityException exception) {
		return exception.entity();
	}

	@ExceptionHandler(AlreadyExistException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorEntity handleAlreadyExistException(ErrorEntityException exception) {
		return exception.entity();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorEntity handleValidRequestException(MethodArgumentNotValidException exception) {
		return new ErrorEntityException(ErrorType.BAD_REQUEST,
				exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()).entity();
	}

	@ExceptionHandler({ MissingServletRequestParameterException.class, HttpMessageNotReadableException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorEntity handleMissingServletRequestParameterException(Exception exception) {
		return new ErrorEntityException(ErrorType.BAD_REQUEST, "유효한 형식의 쿼리 요청이 아닙니다.").entity();
	}

}
