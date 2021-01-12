package br.com.obpc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class UsernameExistingException extends Exception {

	private static final long serialVersionUID = 1L;
	private int status = HttpStatus.UNPROCESSABLE_ENTITY.value();
	private static final String DEFAULT_MESSAGE =  "Username already exist";
	
	

	public UsernameExistingException() {
		super(DEFAULT_MESSAGE);
	}

	public UsernameExistingException(String message) {
		super(message);
	}

	public int getStatus() {
		return this.status;
	}
	
}
