package br.com.obpc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_REQUIRED)
public class PasswordNotPresentException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final int status = HttpStatus.UNPROCESSABLE_ENTITY.value();
	private static final String DEFAULT_MESSASGE = "Password is not present";
	
	public PasswordNotPresentException() {
		super(DEFAULT_MESSASGE);
	}

	public int getStatus() {
		return status;
	}

}
