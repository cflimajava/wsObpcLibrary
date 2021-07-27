package br.com.obpc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class InactiveUserException extends Exception {

	private static final long serialVersionUID = 1L;
	private final int status = HttpStatus.FORBIDDEN.value();
	private static final String DEFAULT_MESSAGE = "User is not active";

	public InactiveUserException() {
		super(DEFAULT_MESSAGE);
	}

	public InactiveUserException(String message) {
		super(message);
	}

	public int getStatus() {
		return status;
	}

}
