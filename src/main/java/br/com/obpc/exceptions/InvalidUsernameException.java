package br.com.obpc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class InvalidUsernameException extends Exception {

	private static final long serialVersionUID = 1L;
	private int status = HttpStatus.PRECONDITION_FAILED.value();
	private static final String DEFAULT_MESSAGE =  "Username is not a valid email address";

	public InvalidUsernameException() {
		super(DEFAULT_MESSAGE);
	}

	public InvalidUsernameException(String message) {
		super(message);
	}

	public int getStatus() {
		return this.status;
	}
	
}
