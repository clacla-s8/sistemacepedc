package br.com.exception;

import java.util.HashMap;
import java.util.Map;

public class ValidacaoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errors = new HashMap<>();
	
	public ValidacaoException(String message) {
        super(message);
    }
	
	public Map<String, String> getErrors() {
		return errors;
	}
	
	public void addError(String fieldNome, String errorMessage) {
		errors.put(fieldNome, errorMessage);
	}
	

}
