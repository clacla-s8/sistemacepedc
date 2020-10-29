package br.com.exception;

public class DaoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DaoException(String mensagem) {
		super(mensagem);
	}
}