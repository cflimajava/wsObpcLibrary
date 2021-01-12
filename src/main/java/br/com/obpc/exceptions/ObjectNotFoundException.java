package br.com.obpc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	private final int status = HttpStatus.NOT_FOUND.value();

	public ObjectNotFoundException(String message) {
		super(message);
	}

	public int getStatus() {
		return status;
	}	

}
