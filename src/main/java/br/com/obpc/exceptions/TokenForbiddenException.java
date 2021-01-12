package br.com.obpc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class TokenForbiddenException extends Exception {

	private static final long serialVersionUID = 1L;
	private final int status = HttpStatus.FORBIDDEN.value();
	private static final String DEFAULT_MESSAGE = "Invalid token";
	
	public TokenForbiddenException() {
		super(DEFAULT_MESSAGE);
	}
	
	public TokenForbiddenException(String message) {
		super(message);
	}

	public int getStatus() {
		return status;
	}

}
