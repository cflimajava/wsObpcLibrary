package br.com.obpc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class CustomerUnprocessableException extends Exception {

	private static final long serialVersionUID = 1L;
	private int status = HttpStatus.UNPROCESSABLE_ENTITY.value();
	private static final String DEFAULT_MESSAGE =  "Erro to process customer data";
	
	public CustomerUnprocessableException() {
		super(DEFAULT_MESSAGE);
	}

	public CustomerUnprocessableException(String message) {
		super(message);
	}

	public int getStatus() {
		return this.status;
	}
	
}
